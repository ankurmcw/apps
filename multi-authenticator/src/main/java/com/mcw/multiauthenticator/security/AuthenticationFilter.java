package com.mcw.multiauthenticator.security;

import com.mcw.multiauthenticator.exception.ResourceNotFoundException;
import com.mcw.multiauthenticator.exception.UnauthorizedException;
import com.mcw.multiauthenticator.security.handler.AuthenticationHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@ConfigurationProperties(prefix = "authentication")
public class AuthenticationFilter extends OncePerRequestFilter {

    private final ApplicationContext applicationContext;
    @Setter
    private Map<String, String> platforms = new HashMap<>();

    public AuthenticationFilter(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            AuthenticationHandler authenticationHandler = getAuthenticationHandler(request.getRequestURI());
            String authorizationToken = request.getHeader("Authorization");
            authenticationHandler.validate(authorizationToken);
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        } catch (ResourceNotFoundException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write(e.getMessage());
        }
    }

    private AuthenticationHandler getAuthenticationHandler(String path) throws ResourceNotFoundException {
        String[] tokens = path.split("/");
        if (tokens.length < 4) {
            throw new ResourceNotFoundException("Invalid path: " + path);
        }
        String platform = tokens[1];
        if (!platforms.containsKey(platform)) {
            throw new ResourceNotFoundException(platform + " is not supported");
        }
        return applicationContext.getBean(platforms.get(platform), AuthenticationHandler.class);
    }
}
