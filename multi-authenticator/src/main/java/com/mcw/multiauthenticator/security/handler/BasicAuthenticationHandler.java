package com.mcw.multiauthenticator.security.handler;

import com.mcw.multiauthenticator.exception.UnauthorizedException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("basic")
public class BasicAuthenticationHandler implements AuthenticationHandler {

    @Override
    public void validate(String authorizationToken) throws UnauthorizedException {
        log.info("Authenticating with basic auth token: {}", authorizationToken);
        if (Objects.isNull(authorizationToken) || !authorizationToken.startsWith("Basic ")) {
            throw new UnauthorizedException("Invalid basic auth token");
        }
        // Remove the "Basic " prefix
        String base64Credentials = authorizationToken.substring(6);

        // Decode the Base64-encoded credentials
        byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);

        // The decoded string should be "username:password"
        String[] credentials = decodedString.split(":", 2);
        if (credentials.length != 2 ||
                !credentials[0].equals("test") ||
                !credentials[1].equals("test")) {
            throw new UnauthorizedException("Invalid basic auth token");
        }
    }
}
