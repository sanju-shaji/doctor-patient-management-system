package com.elixrlabs.doctorpatientmanagementsystem.util;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Util class to retrieve messages from the messages.properties file
 */
@Component
public class MessageUtil {
    private final MessageSource messageSource;

    public MessageUtil(MessageSource messageSource) {

        this.messageSource = messageSource;
    }

    public String getMessage(String Key, Object... args) {

        return messageSource.getMessage(Key, args, null);
    }
}
