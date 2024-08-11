package com.security;

import com.dto.AdminDTO;
import com.dto.ErrorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Set<String> allowedPaths = new HashSet<>();

    static {
        allowedPaths.add("/v1/admin/signIn");
        allowedPaths.add("/v1/admin/signUp");
    }

    private final CustomAuthenticationProvider customAuthenticationProvider;

    public UsernamePasswordAuthFilter(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {
        final String servletPath = httpServletRequest.getServletPath();
        final String requestURI = httpServletRequest.getRequestURI();
        final String servletRequest = httpServletRequest.getMethod();
        final ServletInputStream servletRequestInputStream = httpServletRequest.getInputStream();
        System.out.println("servletPath: " + servletPath);
        System.out.println("requestURI: " + requestURI);
        if (servletPath.startsWith("/webhook") || servletPath.startsWith("/ws") || servletPath.startsWith("/api/auth")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        if ((allowedPaths.contains(servletPath) || allowedPaths.contains(requestURI)) && HttpMethod.POST.matches(servletRequest)) {
            AdminDTO adminDTO = MAPPER.readValue(servletRequestInputStream, AdminDTO.class);
            try {
                Authentication authentication = customAuthenticationProvider.validateCredentials(adminDTO);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                AdminDTO authenticatedAdmin = (AdminDTO) authentication.getPrincipal();
                httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
                httpServletResponse.setStatus(HttpStatus.OK.value());
                MAPPER.writeValue(httpServletResponse.getWriter(), authenticatedAdmin);
                return;
            } catch (RuntimeException e) {
                SecurityContextHolder.clearContext();
                ErrorDTO errorDTO = new ErrorDTO(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized", "ALOHA");
                String errorJson = MAPPER.writeValueAsString(errorDTO);
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.setContentType("application/json");
                httpServletResponse.getWriter().write(errorJson);
                return;
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}