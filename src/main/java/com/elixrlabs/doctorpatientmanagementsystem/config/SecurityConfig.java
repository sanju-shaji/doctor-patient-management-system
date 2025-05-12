package com.elixrlabs.doctorpatientmanagementsystem.config;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.util.AuthEntryPointUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Security configuration class to configure the security filter chain to implement OAuth2.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final MessageUtil messageUtil;

    public SecurityConfig(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.
                authorizeHttpRequests(auth ->
                        auth.requestMatchers(ApiConstants.LOGIN_END_POINT, ApiConstants.REGISTER_END_POINT).permitAll().
                                anyRequest().authenticated()).
                oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()).
                        authenticationEntryPoint(new AuthEntryPointUtil(messageUtil))).
                csrf(AbstractHttpConfigurer::disable).
                build();
    }
}
