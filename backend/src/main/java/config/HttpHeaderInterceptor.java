package com.lunetras.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HttpHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestUri = request.getRequestURI();

        if ("GET".equalsIgnoreCase(request.getMethod())) {

            if (requestUri.contains("/alunos") || requestUri.contains("/turmas")) {
                response.setHeader("Cache-Control", "public, max-age=300");
                response.setHeader("ETag", String.valueOf(System.nanoTime()));
            }
         
            else if (requestUri.contains("/relatorios")) {
                response.setHeader("Cache-Control", "public, max-age=3600");
            }
            else if (requestUri.contains("/auth")) {
                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Expires", "0");
            }
        } else {
            
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        }

        
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");

       
        response.setHeader("Vary", "Accept-Encoding");

        return true;
    }
}
