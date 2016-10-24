package com.near.friendly.resources;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Security resource
 */
@RestController
@RequestMapping("/api/security")
public class SecurityResource {



    /**
     * Generate a new csrf token to be used by the http client in the next call
     *
     * @param token the token
     * @return a new unique Csrf token
     */
    @RequestMapping("/csrf")
    public CsrfToken csrfToken(CsrfToken token) {
         return token;
    }

}
