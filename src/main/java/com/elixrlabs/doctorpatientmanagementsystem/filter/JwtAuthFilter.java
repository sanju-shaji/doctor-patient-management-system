package com.elixrlabs.doctorpatientmanagementsystem.filter;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidJwtTokenException;
import com.elixrlabs.doctorpatientmanagementsystem.service.CustomUserDetailsService;
import com.elixrlabs.doctorpatientmanagementsystem.util.JWTUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter to authenticate requests using JWT tokens.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private MessageUtil messageUtil;

    /**
     * Extracts and validates JWT from request header and sets authentication in context.
     */
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(ApplicationConstants.AUTH_HEADER);
        String token = null;
        String userName = null;
        if (authHeader != null && authHeader.startsWith(ApplicationConstants.BEARER_PREFIX)) {
            token = authHeader.substring(7);
            userName = jwtUtil.extractUserNameFromToken(token);
        }
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
        filterChain.doFilter(request, response);
    }
}
