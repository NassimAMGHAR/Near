package com.near.friendly.service;

import com.near.friendly.core.Constants;
import com.near.friendly.core.model.user.UserSession;
import com.near.friendly.core.utils.DigestUtils;
import com.near.friendly.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

/**
 *
 */
@Service
public class CustomPersistentRememberMeServices extends AbstractRememberMeServices {

    private final Logger log = LoggerFactory.getLogger(CustomPersistentRememberMeServices.class);

    // Token is valid for one month
    private static final int TOKEN_VALIDITY_DAYS = 31;

    private static final int TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * TOKEN_VALIDITY_DAYS;


    private final UserService userService;

    @Inject
    public CustomPersistentRememberMeServices(final UserService userService) {
        super(Constants.SECRET_KEY, userService);
        this.userService = userService;
    }

    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request,
                                                 HttpServletResponse response) {

        final UserSession session = getUserSession(cookieTokens);

        final String login = session.getUser().getLogin();

        // Token also matches, so login is valid. Update the token value, keeping the *same* id and generate a new session.
        log.debug("Refreshing persistent login token for user '{}', series '{}'", login, session.getId());

        session.setDate(LocalDate.now());
        session.setValue(DigestUtils.generate(Constants.DEFAULT_USER_SESSION_TOKEN_LENGTH));
        session.setIpAddress(request.getRemoteAddr());
        session.setUserAgent(request.getHeader("User-Agent"));
        try {
            userService.saveUserSession(session);
            addCookie(session, request, response);
        } catch (DataAccessException e) {
            log.error("Failed to update token: ", e);
            throw new RememberMeAuthenticationException("Autologin failed due to data access problem", e);
        }
        return getUserDetailsService().loadUserByUsername(login);
    }

    @Override
    protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response,
                                  Authentication successfulAuthentication) {

        final String login = successfulAuthentication.getName();

        log.debug("Creating new persistent login for user {}", login);

        final UserSession userSession = userService.getUserByLogin(login)
                .map(u -> new UserSession(u, request.getRemoteAddr(), request.getHeader("User-Agent")))
                .orElseThrow(() -> new UsernameNotFoundException("User " + login + " was not found in the database"));

        boolean saved = userService.saveUserSession(userSession);

        if (saved) {
            addCookie(userSession, request, response);
        }
    }

    /**
     * When logout occurs, only invalidate the current token, and not all user sessions.
     * <p>
     * The standard Spring Security implementations are too basic: they invalidate all tokens for the
     * current user, so when he logs out from one browser, all his other sessions are destroyed.
     */
    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String rememberMeCookie = extractRememberMeCookie(request);
        if (rememberMeCookie != null && rememberMeCookie.length() != 0) {
            try {
                String[] cookieTokens = decodeCookie(rememberMeCookie);
                UserSession userSession = getUserSession(cookieTokens);
                userService.deleteSession(userSession);
            } catch (InvalidCookieException ice) {
                log.info("Invalid cookie, no persistent token could be deleted", ice);
            } catch (RememberMeAuthenticationException rmae) {
                log.debug("No persistent token found, so no token could be deleted", rmae);
            }
        }
        super.logout(request, response, authentication);
    }

    /**
     * Validate the token and return it.
     */
    private UserSession getUserSession(String[] cookieTokens) {
        if (cookieTokens.length != 2) {
            throw new InvalidCookieException("Cookie token did not contain " + 2 +
                    " tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
        }

        String presentedSeries = cookieTokens[0];
        String presentedToken = cookieTokens[1];
        Optional<UserSession> optionalUserSession = userService.getUserSessionById(presentedSeries);

        if (!optionalUserSession.isPresent()) {
            // No series match, so we can't authenticate using this cookie
            throw new RememberMeAuthenticationException("No persistent userSession found for series id: " + presentedSeries);
        }

        UserSession userSession = optionalUserSession.get();

        // We have a match for this user/series combination
        log.info("presentedToken={} / tokenValue={}", presentedToken, userSession.getValue());

        if (!presentedToken.equals(userSession.getValue())) {
            // Token doesn't match series value. Delete this session and throw an exception.
            userService.deleteSession(userSession);
            throw new CookieTheftException("Invalid remember-me userSession (Series/userSession) mismatch. Implies previous " +
                    "cookie theft attack.");
        }

        //check if still valid
        if (userSession.getDate().plusDays(TOKEN_VALIDITY_DAYS).isBefore(LocalDate.now())) {
            userService.deleteSession(userSession);
            throw new RememberMeAuthenticationException("Remember-me login has expired");
        }
        return userSession;
    }


    private void addCookie(UserSession userSession, HttpServletRequest request, HttpServletResponse response) {
        setCookie(
                new String[]{userSession.getId(), userSession.getValue()}, TOKEN_VALIDITY_SECONDS, request, response);
    }
}
