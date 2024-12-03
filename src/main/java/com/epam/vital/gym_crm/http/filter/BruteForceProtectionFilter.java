package com.epam.vital.gym_crm.http.filter;

import com.epam.vital.gym_crm.domain.service.BruteForceProtectionService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class BruteForceProtectionFilter extends HttpFilter {
    private final BruteForceProtectionService bruteForceProtectionService;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String clientIp = request.getRemoteAddr();
        if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) bruteForceProtectionService.recordFailedAttempt(clientIp);

        if (bruteForceProtectionService.isBlocked(clientIp)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Too many failed attempts. Try again later.");
            return;
        }

        chain.doFilter(request, response);
    }
}