package com.kuraiji.blog.security;

import com.kuraiji.blog.exception.AuthorizationInvalidException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            Long userId = (Long) request.getAttribute("userId");
            BlogUserDetailsService blogUserDetailsService = (BlogUserDetailsService) userDetailsService;
            BlogUserDetails blogUserDetails = (BlogUserDetails) blogUserDetailsService.loadUserById(userId);
        } catch (Exception ex) {
            log.warn("Failed to confirm authorization");
            throw new AuthorizationInvalidException("Not Authorized");
        }
        filterChain.doFilter(request, response);
    }
}
