package com.elixrlabs.doctorpatientmanagementsystem.config;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.filter.JwtAuthFilter;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.CustomAuthEntryPoint;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Security configuration class to configure the security filter chain to implement OAuth2.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MessageUtil messageUtil;
    private final UserDetailsService userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;
    @Value("${isOAuth2Enabled}")
    private Boolean isOAuth2Enabled;

    public SecurityConfig(MessageUtil messageUtil, UserDetailsService userDetailsService, JwtAuthFilter jwtAuthFilter) {
        this.messageUtil = messageUtil;
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * Configures the security filter chain including JWT, OAuth2, and exception handling.
     */
    @Bean
    public SecurityFilterChain customFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                        auth.requestMatchers(ApiConstants.REGISTER_END_POINT, ApiConstants.AUTH_END_POINT).permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);

        if (isOAuth2Enabled) {
            http.oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()).
                    authenticationEntryPoint(new CustomAuthEntryPoint(messageUtil)));
        } else {
            http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).exceptionHandling(ex ->
                    ex.authenticationEntryPoint((request, response, authException) -> {
                        response.setContentType(ApplicationConstants.CONTENT_TYPE);
                        response.setStatus(401);
                        BaseResponse errorResponse = new BaseResponse();
                        ObjectMapper objectMapper = new ObjectMapper();
                        errorResponse.setSuccess(false);
                        errorResponse.setErrors(List.of(messageUtil.getMessage(MessageKeyEnum.INVALID_JWT_TOKEN.getKey())));
                        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                    }));
        }
        return http.build();
    }

    /**
     * Provides the password encoder bean using BCrypt algorithm.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines an authentication manager using DAO authentication provider
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }
}
