package com.epam.vital.gym_crm.interceptor;

import com.epam.vital.gym_crm.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
@ComponentScan(basePackages = "com.epam.vital.gym_crm.service")
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final UserService userService;

    @Autowired
    public AuthenticationInterceptor(UserService userService) {
        this.userService = userService;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Called - Request URI: {}; Request Method: {};", request.getRequestURI(), request.getMethod());

        String path = request.getRequestURI();

        if (path.equals("/trainer/register") || path.equals("/trainee/register")) {
            return true;
        }

        boolean isAuthenticated = authenticate(request);
        if (!isAuthenticated) {
            log.error("User not authenticated; Bad credentials.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized. Bad credentials.");
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
        log.info("Response status: {};", response.getStatus());
        if (ex != null) {
            log.error("Exception thrown during processing: ", ex);
        }
    }

    private boolean authenticate(HttpServletRequest request) {
        return userService.authenticateUser(request.getParameter("username"), request.getParameter("password"));
    }
}