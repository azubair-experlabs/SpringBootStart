package com.experlabs.SpringBootStart.user.configs;

import com.experlabs.SpringBootStart.core.models.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonData = objectMapper.writeValueAsString(
        new ResponseBody(HttpStatus.UNAUTHORIZED.value(), authException.getMessage()));
    PrintWriter writer = response.getWriter();
    writer.println(jsonData);
  }
}