package com.mcw.multiauthenticator.security.handler;

import com.mcw.multiauthenticator.exception.UnauthorizedException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("jwt")
public class JwtAuthenticationHandler implements AuthenticationHandler {

    @Override
    public void validate(String authorizationToken) throws UnauthorizedException {
        if (Objects.isNull(authorizationToken) || !authorizationToken.startsWith("Bearer test")) {
            throw new UnauthorizedException("Invalid token");
        }
    }
}
