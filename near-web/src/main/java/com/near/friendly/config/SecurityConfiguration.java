package com.near.friendly.config;

import com.near.friendly.core.Constants;
import com.near.friendly.core.utils.CookieUtils;
import com.near.friendly.filter.security.CsrfCookieGeneratorFilter;
import com.near.friendly.service.user.UserService;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.security.web.csrf.CsrfFilter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ayassinov
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //@Inject
    //private CustomPersistentRememberMeServices rememberMeServices;

    private final UserService userService;

    @Inject
    public SecurityConfiguration(@SuppressWarnings("SpringJavaAutowiringInspection")
                                 final UserService userDetailsService) {
        this.userService = userDetailsService;
    }

    @Inject
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        try {
            //when passing userService implementation (the DaoAuthenticationConfigurer is used as authentication mechanism)
            auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //ignore any HTTP call with OPTIONS verbs
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .and()
                //config exception for CSRF token
                .addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(new CrsfAccessDeniedHandler()) //distinguish the CsrfException from other AccessDeniedExceptions
                .authenticationEntryPoint(new Http401AuthenticationEntryPoint("Basic realm=\"Rest\""))
                //.and()
                //persist session in the http client
                //.rememberMe()
                //.rememberMeServices(rememberMeServices)
                //.rememberMeParameter("remember-me")
                //.key(Constants.SECRET_KEY)
                .and()
                //config logout
                .formLogin() //use: username and password to submit from http client
                .loginProcessingUrl("/api/login")
                .successHandler(new AuthenticationSuccessHandler())
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .permitAll()
                .and()
                //config logout
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(new AuthenticationLogoutSuccessHandler())
                .deleteCookies(CookieUtils.SESSION_COOKIE_NAME, CookieUtils.CSRF_COOKIE_NAME)
                .permitAll()
                .and()
                //disable frame
                .headers()
                .frameOptions()
                .disable()
                .and()
                .authorizeRequests()
                //all security end points
                .antMatchers("/api/security/csrf").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/logout").permitAll()
                .antMatchers("/api/authenticate").permitAll()
                //all the others end points
                .antMatchers("/api/**").authenticated()
                //need admin role for all management sub calls
                .antMatchers("/management/**").hasAuthority(Constants.ADMIN);

    }

    /**
     * Spring Security success handler, specialized for Ajax requests that prevent redirects
     */
    private class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        /***
         * On successful authentication return HTTP OK status instead of redirecting with the default spring
         * implementation
         *
         * @param request        http request
         * @param response       http response
         * @param authentication authentication object
         * @throws IOException
         * @throws ServletException
         */
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                            Authentication authentication) throws IOException, ServletException {

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }


    /**
     * Spring Security logout handler, specialized for Ajax requests.
     */
    private class AuthenticationLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler
            implements LogoutSuccessHandler {

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                    Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }


    /**
     * An implementation of AccessDeniedHandler by wrapping the AccessDeniedHandlerImpl.
     * <p>
     * In addition to sending a 403 (SC_FORBIDDEN) HTTP error code, it will remove the invalid CSRF cookie from the browser
     * side when a CsrfException occurs. In this way the browser side application, e.g. JavaScript code, can
     * distinguish the CsrfException from other AccessDeniedExceptions and perform more specific operations. For instance,
     * send a GET HTTP method to obtain a new CSRF token.
     *
     * @see AccessDeniedHandlerImpl
     */
    private class CrsfAccessDeniedHandler implements AccessDeniedHandler {

        private AccessDeniedHandlerImpl accessDeniedHandlerImpl = new AccessDeniedHandlerImpl();

        public void handle(HttpServletRequest request, HttpServletResponse response,
                           AccessDeniedException accessDeniedException) throws IOException, ServletException {

            if (accessDeniedException instanceof CsrfException && !response.isCommitted()) {
                // Remove the session cookie so that client knows it's time to obtain a new CSRF token
                final Cookie cookie = CookieUtils.removeCSRF();
                response.addCookie(cookie);
            }

            accessDeniedHandlerImpl.handle(request, response, accessDeniedException);
        }
    }


}
