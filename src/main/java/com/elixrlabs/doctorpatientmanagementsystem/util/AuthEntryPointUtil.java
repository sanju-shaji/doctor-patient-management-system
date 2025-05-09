package com.elixrlabs.doctorpatientmanagementsystem.util;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.List;

/**
 * Class to define custom response to the exceptions thrown by the resource server
 */
public class AuthEntryPointUtil implements AuthenticationEntryPoint {
    private final MessageUtil messageUtil;

    public AuthEntryPointUtil(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        BaseResponse errorResponse = new BaseResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        errorResponse.setSuccess(false);
        if (authException.getClass() == InvalidBearerTokenException.class) {
            errorResponse.setErrors(List.of(messageUtil.getMessage(MessageKeyEnum.INVALID_ACCESS_TOKEN.getKey())));
        }
        if (authException.getClass() == InsufficientAuthenticationException.class) {
            errorResponse.setErrors(List.of(messageUtil.getMessage(MessageKeyEnum.MISSING_ACCESS_TOKEN.getKey())));
        }
        response.setContentType(ApplicationConstants.APPLICATION_JSON);
        response.setStatus(401);
        response.getWriter().print(objectMapper.writeValueAsString(errorResponse));
    }
}
