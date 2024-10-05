package com.mcw.multiauthenticator.security.handler;

import com.mcw.multiauthenticator.exception.UnauthorizedException;

public interface AuthenticationHandler {

    void validate(String authorizationToken) throws UnauthorizedException;
}
