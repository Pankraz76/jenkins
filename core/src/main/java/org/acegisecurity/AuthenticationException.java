/*
 * The MIT License
 *
 * Copyright 2020 CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.acegisecurity;

import hudson.security.UserMayOrMayNotExistException2;
import org.acegisecurity.providers.ProviderNotFoundException;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.springframework.dao.DataAccessException;

/**
 * @deprecated use {@link org.springframework.security.core.AuthenticationException}
 */
@Deprecated
public abstract class AuthenticationException extends AcegiSecurityException {

    private Authentication authentication;
    private Object extraInformation;

    protected AuthenticationException(String msg) {
        super(msg);
    }

    protected AuthenticationException(String msg, Object extraInformation) {
        super(msg);
        this.extraInformation = extraInformation;
    }

    protected AuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Object getExtraInformation() {
        return extraInformation;
    }

    public void clearExtraInformation() {
        extraInformation = null;
    }

    @Override
    public org.springframework.security.core.AuthenticationException toSpring() {
        return new org.springframework.security.core.AuthenticationException(toString(), this) {};
    }

    /**
     * @return either an {@link AuthenticationException} or a {@link DataAccessException}
     */
    public static RuntimeException fromSpring(org.springframework.security.core.AuthenticationException x) {
        if (x instanceof org.springframework.security.authentication.BadCredentialsException exception8) {
            return BadCredentialsException.fromSpring(exception8);
        } else if (x instanceof org.springframework.security.authentication.AuthenticationServiceException exception7) {
            return AuthenticationServiceException.fromSpring(exception7);
        } else if (x instanceof org.springframework.security.authentication.AccountExpiredException exception6) {
            return AccountExpiredException.fromSpring(exception6);
        } else if (x instanceof org.springframework.security.authentication.CredentialsExpiredException exception5) {
            return CredentialsExpiredException.fromSpring(exception5);
        } else if (x instanceof org.springframework.security.authentication.DisabledException exception4) {
            return DisabledException.fromSpring(exception4);
        } else if (x instanceof org.springframework.security.authentication.InsufficientAuthenticationException exception3) {
            return InsufficientAuthenticationException.fromSpring(exception3);
        } else if (x instanceof org.springframework.security.authentication.LockedException exception2) {
            return LockedException.fromSpring(exception2);
        } else if (x instanceof org.springframework.security.authentication.ProviderNotFoundException exception1) {
            return ProviderNotFoundException.fromSpring(exception1);
        } else if (x instanceof UserMayOrMayNotExistException2 && x.getCause() instanceof DataAccessException) {
            return (DataAccessException) x.getCause();
        } else if (x instanceof org.springframework.security.core.userdetails.UsernameNotFoundException exception) {
            return UsernameNotFoundException.fromSpring(exception);
        } else {
            return new AuthenticationException(x.toString(), x) {};
        }
    }

}
