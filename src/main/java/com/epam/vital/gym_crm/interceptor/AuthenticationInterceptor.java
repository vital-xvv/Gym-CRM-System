package com.epam.vital.gym_crm.interceptor;

import com.epam.vital.gym_crm.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@ComponentScan(basePackages = "com.epam.vital.gym_crm.service")
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final UserService userService;

    @Autowired
    public AuthenticationInterceptor(UserService userService) {
        this.userService = userService;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();

        // Add conditions to skip authentication for specific methods
        if (path.equals("/trainer/register") || path.equals("/trainee/register")) {
            return true;
        }

        // Call your custom authentication method
        boolean isAuthenticated = authenticate(request);
        if (!isAuthenticated) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized. Bad credentials.");
            return false;
        }
        return true;
    }

    private boolean authenticate(HttpServletRequest request) {
        return userService.authenticateUser(request.getParameter("username"), request.getParameter("password"));
    }
}