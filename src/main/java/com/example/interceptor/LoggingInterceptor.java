package com.example.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        String uri = request.getRequestURI();
        System.out.println("Request URL: " + uri);

        if (uri.contains("/login")) {
            System.out.println("Login attempt detected");
        }

        if (uri.contains("/register")) {
            HttpSession session = request.getSession(false);
            String user = (session != null) ? (String) session.getAttribute("user") : "UNKNOWN";
            System.out.println("Course registration attempt by: " + user);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        String uri = request.getRequestURI();

        if (uri.contains("/login")) {
            System.out.println("Login process completed");
        }

        if (uri.contains("/register")) {
            System.out.println("Registration process completed");
        }
    }
}