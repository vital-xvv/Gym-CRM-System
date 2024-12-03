package com.epam.vital.gym_crm.http.interceptor;

import com.epam.vital.gym_crm.actuator.CountApiCallsGauge;
import com.epam.vital.gym_crm.domain.service.UserService;
import com.epam.vital.gym_crm.http.util.HttpUrlsDict;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final UserService userService;
    private final CountApiCallsGauge apiCallsGauge;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Called - Request URI: {}; Request Method: {};", request.getRequestURI(), request.getMethod());
        apiCallsGauge.apiCalled();
        String path = request.getRequestURI();

        if (path.equals("/trainer/register") || path.equals("/trainee/register") || path.equals(HttpUrlsDict.USER_URL + HttpUrlsDict.CURRENT_VERSION + "/login")) {
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
        log.info("Response status: {};", response.getStatus());
        if (ex != null) {
            log.error("Exception thrown during processing: ", ex);
        }
    }

    private boolean authenticate(HttpServletRequest request) {
        return true;
    }
}