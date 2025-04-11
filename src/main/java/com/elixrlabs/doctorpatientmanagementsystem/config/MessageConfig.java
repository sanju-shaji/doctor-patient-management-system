package com.elixrlabs.doctorpatientmanagementsystem.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Configuration class for message source setup in Spring Boot.
 * loads messages.properties file
 */
@Configuration
public class MessageConfig {
    public MessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages");
        source.setDefaultEncoding("UTF-8");
        return source;
    }
}
