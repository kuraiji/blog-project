package com.kuraiji.blog.security;

import com.kuraiji.blog.common.permissions.PermissionFetcher;
import com.kuraiji.blog.common.permissions.impl.*;
import com.kuraiji.blog.domain.dto.CommentDto;
import com.kuraiji.blog.domain.dto.PostDto;
import com.kuraiji.blog.domain.entity.PermissionScope;
import com.kuraiji.blog.exception.AuthorizationInvalidException;
import com.kuraiji.blog.exception.CommentNotFoundException;
import com.kuraiji.blog.exception.PostNotFoundException;
import com.kuraiji.blog.exception.UriNotFoundException;
import com.kuraiji.blog.services.CommentService;
import com.kuraiji.blog.services.PostService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final PostService postService;

    private final CommentService commentService;

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
        List<String> patterns = Arrays.asList("/v1/auth", "/actuator/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**");
        return patterns.stream().anyMatch(p -> pathMatcher.match(p, path));

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
        switch (info.getEndpoint()) {
            case "users":
                return user.getId() == Long.parseLong(info.getPathVariable());
            case "posts":
                UUID postId = UUID.fromString(info.getPathVariable());
                PostDto post = postService.findOne(postId).orElseThrow(() -> new PostNotFoundException(postId));
                return Objects.equals(user.getId(), post.getOwner().getId());
            case "comments":
                UUID commentId = UUID.fromString(info.getPathVariable());
                CommentDto comment = commentService.findOne(commentId).orElseThrow(() -> new CommentNotFoundException(commentId));
                return Objects.equals(user.getId(), comment.getOwner().getId());
            default:
                return false;
        }
    }
}
