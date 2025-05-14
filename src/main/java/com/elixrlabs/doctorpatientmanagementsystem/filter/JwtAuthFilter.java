package com.elixrlabs.doctorpatientmanagementsystem.filter;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.service.CustomUserDetailsService;
import com.elixrlabs.doctorpatientmanagementsystem.util.JwtUtil;
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

/**
 * Filter to authenticate requests using JWT tokens.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MessageUtil messageUtil;

    /**
     * Extracts and validates JWT from request header and sets authentication in context.
     */
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        String authHeader = request.getHeader(ApplicationConstants.AUTH_HEADER);
        String token = null;
        String userName = null;
        if (authHeader != null && authHeader.startsWith(ApplicationConstants.BEARER_PREFIX)) {
            token = authHeader.substring(7);
            try {
                String issuer = jwtUtil.extractIssuerFromToken(token);
                if (issuer.contains("elixr")) {
                    userName = jwtUtil.extractUserNameFromToken(token);
                    if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
                        if (jwtUtil.validateToken(userName, userDetails, token)) {
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        }
                    }

                }

                else {
                    // This is your internal JWT
                }
            }
            catch (Exception exception){
            }


        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authHeader = request.getHeader(ApplicationConstants.AUTH_HEADER);
        String token = null;
        String userName = null;
        if (authHeader != null && authHeader.startsWith(ApplicationConstants.BEARER_PREFIX)) {
            token = authHeader.substring(7);
            try {
                String issuer = jwtUtil.extractIssuerFromToken(token);
                if (issuer.contains("elixr")) {
                    return false;
                } else {
                    return true;
                }
            } catch (Exception exception) {
                return true;
            }
        }
        return true;
    }
}
