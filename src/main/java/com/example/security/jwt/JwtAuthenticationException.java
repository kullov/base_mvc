package com.example.security.jwt;

import org.springframework.security.core.AuthenticationException;

/**
 * The class Jwt authentication exception.
 *
 * @author MinhNB<br>
 * <p>Mail: minhnb.it@pm.me</p>
 * @since 2021-10-09
 */
public class JwtAuthenticationException extends AuthenticationException {

    /**
     * Constructs an {@code AuthenticationException} with the specified message and no root cause.
     *
     * @param msg the detail message
     */
    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}