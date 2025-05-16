package com.elixrlabs.doctorpatientmanagementsystem.util;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.response.AuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * Custom authentication entry point for handling unauthorized access and JWT-related errors.
 */
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final MessageUtil messageUtil;

    public CustomAuthEntryPoint(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;

    }

    /**
     * Handles authentication exceptions and sends a structured error response.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        AuthResponse authResponse = new AuthResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        if (authException.getCause() instanceof JwtException) {
            authResponse.setErrors((authException.getLocalizedMessage()));
        }
        if (authException.getClass() == InsufficientAuthenticationException.class) {
            authResponse.setErrors(messageUtil.getMessage(MessageKeyEnum.MISSING_ACCESS_TOKEN.getKey()));
        }
        authResponse.setSuccess(false);
        response.setContentType(ApplicationConstants.APPLICATION_JSON);
        response.setStatus(401);
        response.getWriter().print(objectMapper.writeValueAsString(authResponse));
    }
}
