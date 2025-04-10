package com.elixrlabs.doctorpatientmanagementsystem.util;

import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * Util class to retrieve messages from the messages.properties file
 */
@Component
public class MessageUtil {
    @Autowired
    private MessageSource messageSource;

    public String getMessage(MessageKeyEnum Key, Object args) {
        return messageSource.getMessage(Key.toString(), (Object[]) args, null);
    }
}
