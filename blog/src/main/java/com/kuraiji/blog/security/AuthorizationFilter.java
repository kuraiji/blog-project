package com.kuraiji.blog.security;

import com.kuraiji.blog.common.permissions.PermissionFetcher;
import com.kuraiji.blog.common.permissions.impl.*;
import com.kuraiji.blog.domain.entity.PermissionScope;
import com.kuraiji.blog.exception.AuthorizationInvalidException;
import com.kuraiji.blog.exception.UriNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

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
            ExtractedUriInfo info = extractUriInfo(request);
            if(!isAuthorized(blogUserDetails, info)) throw new AuthorizationInvalidException("Not Authorized");
            filterChain.doFilter(request, response);
        } catch (UriNotFoundException ex) {
            log.warn(ex.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        return pathMatcher.match("/v1/auth", path) ||
                pathMatcher.match("/actuator/**", path) ||
                pathMatcher.match("/v3/api-docs/**", path) ||
                pathMatcher.match("/swagger-ui.html", path) ||
                pathMatcher.match("/swagger-ui/**", path);

    }

    private ExtractedUriInfo extractUriInfo(HttpServletRequest request) throws UriNotFoundException {
        try {
            String formatedString = request.getServletPath()
                    .replaceFirst("/", "")
                    .replaceAll("/$", "");
            List<String> parts = List.of(formatedString.split("/"));
            return ExtractedUriInfo.builder()
                    .restType(request.getMethod())
                    .endpoint(parts.get(1))
                    .pathVariable(parts.size() > 2 ? parts.get(2) : "")
                    .build();
        } catch (Exception e) {
            throw new UriNotFoundException("Invalid URI");
        }
    }

    private boolean isAuthorized(BlogUserDetails user, ExtractedUriInfo info) {
        PermissionFetcher permissionFetcher;
        switch (info.getEndpoint()) {
            case "roles":
                permissionFetcher = new RolePermissionFetcher();
                break;
            case "users":
                permissionFetcher = new UserPermissionFetcher();
                break;
            case "permissions":
                permissionFetcher = new PermissionPermissionFetcher();
                break;
            case "posts":
                permissionFetcher = new PostPermissionFetcher();
                break;
            case "comments":
                permissionFetcher = new CommentPermissionFetcher();
                break;
            default:
                return false;
        }
        PermissionScope scope;
        switch (info.getRestType()) {
            case "GET":
                scope = user.isAuthorized(permissionFetcher.get());
                break;
            case "POST":
                scope = user.isAuthorized(permissionFetcher.post());
                break;
            case "PUT":
                scope = user.isAuthorized(permissionFetcher.put());
                break;
            case "PATCH":
                scope = user.isAuthorized(permissionFetcher.patch());
                break;
            case "DELETE":
                scope = user.isAuthorized(permissionFetcher.delete());
                break;
            default:
                return false;
        }
        if(scope == PermissionScope.NONE) return false;
        if(scope == PermissionScope.ALL) return true;
        return user.getId() == Long.parseLong(info.getPathVariable());
    }
}
