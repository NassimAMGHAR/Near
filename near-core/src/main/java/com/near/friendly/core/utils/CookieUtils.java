package com.near.friendly.core.utils;

import javax.servlet.http.Cookie;

/**
 * A convenient set of methods that checks and create http cookies.
 */
public class CookieUtils {

    public static String CSRF_COOKIE_NAME = "CSRF-TOKEN";

    public static String SESSION_COOKIE_NAME = "SESSION";

    private CookieUtils() {
        /*Singleton*/
    }

    public static Cookie removeCSRF() {
        return createCookie(CSRF_COOKIE_NAME, "", 0);
    }

    public static Cookie createCSRF(final String value) {
        return createCookie(CSRF_COOKIE_NAME, value, -1);
    }

    private static Cookie createCookie(final String name, final String value, final int maxAge) {
        final Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
        return cookie;
    }
}
