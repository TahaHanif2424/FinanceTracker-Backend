package com.FutureConnect.FutureConnect.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);

    System.out.println("=============== REQUEST START ===============");
    System.out.println("Method: " + request.getMethod());
    System.out.println("URI: " + request.getRequestURI());
    System.out.println("Content-Type: " + request.getContentType());
    System.out.println("Content-Length: " + request.getContentLength());

    System.out.println("Headers:");
    Collections.list(request.getHeaderNames())
        .forEach(
            headerName ->
                System.out.println("  " + headerName + ": " + request.getHeader(headerName)));

    filterChain.doFilter(wrappedRequest, response);

    String body = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
    if (!body.isEmpty()) {
      System.out.println("Body: " + body);
    }

    System.out.println("Response Status: " + response.getStatus());
    System.out.println("=============== REQUEST END ===============");
  }
}
