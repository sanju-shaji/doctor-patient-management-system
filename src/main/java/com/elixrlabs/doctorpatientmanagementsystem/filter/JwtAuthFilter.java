package com.elixrlabs.doctorpatientmanagementsystem.filter;

import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidJwtTokenException;
import com.elixrlabs.doctorpatientmanagementsystem.service.CustomUserDetailsService;
import com.elixrlabs.doctorpatientmanagementsystem.util.JwtUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT authentication filter that validates tokens and sets security context for authenticated users.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final MessageUtil messageUtil;

    public JwtAuthFilter(CustomUserDetailsService customUserDetailsService, JwtUtil jwtUtil, MessageUtil messageUtil) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.messageUtil = messageUtil;
    }

    /**
     * Filters incoming requests, validates JWT, and sets authentication if token is valid.
     */
    @SneakyThrows
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
        String token = jwtUtil.extractToken(request);
        if (jwtUtil.isInternalJwt(token)) {
            String userName = jwtUtil.extractUserNameFromToken(token);
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
                if (jwtUtil.validateToken(userName, userDetails, token)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    throw new InvalidJwtTokenException(messageUtil.getMessage(MessageKeyEnum.INVALID_JWT_TOKEN.getKey()));
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}