package com.near.friendly.filter.security;

import com.near.friendly.core.utils.CookieUtils;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter used to put the CSRF token generated by Spring Security in a cookie for use by AngularJS.
 */
public class CsrfCookieGeneratorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Spring put the CSRF token in session attribute "_csrf"
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");

        // Send the cookie only if the token has changed
        String actualToken = request.getHeader("X-CSRF-TOKEN");
        if (actualToken == null || !actualToken.equals(csrfToken.getToken())) {
            // Session cookie that will be used by AngularJS
            final Cookie cookie = CookieUtils.createCSRF(csrfToken.getToken());
            response.addCookie(cookie);
        }

        filterChain.doFilter(request, response);
    }
}
