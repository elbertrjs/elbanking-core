package com.elbanking.core.controller.authentication;

import com.elbanking.core.enums.StatusCodeEnum;
import com.elbanking.core.model.HTTPResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        StatusCodeEnum statusCode = StatusCodeEnum.INVALID_CREDENTIAL;

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        HTTPResult httpResult = HTTPResult
                .builder()
                .status(statusCode.getHttpStatusCode())
                .message(statusCode.getMessage())
                .build();

        response.setContentType("application/json");
        response.setStatus(statusCode.getHttpStatusCode());
        response.getWriter().write(objectWriter.writeValueAsString(httpResult));
    }
}
