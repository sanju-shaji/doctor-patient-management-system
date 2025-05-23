package com.elixrlabs.doctorpatientmanagementsystem.validation;

import com.elixrlabs.doctorpatientmanagementsystem.dto.UsersDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.UserInputValidationException;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates user input for username and password fields.
 */
@Component
public class UserNameAndPasswordValidator {

    private final MessageUtil messageUtil;

    public UserNameAndPasswordValidator(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    /**
     * Checks if username and password are non-empty; throws exception if validation fails.
     */
    public void validateUserNameAndPassword(UsersDto usersDto) {
        List<String> errors = new ArrayList<>();
        if (!StringUtils.hasText(usersDto.getUserName())) {
            errors.add(messageUtil.getMessage(MessageKeyEnum.USERNAME_BLANK.getKey()));
        }

        if (!StringUtils.hasText(usersDto.getPassword())) {
            errors.add(messageUtil.getMessage(MessageKeyEnum.PASSWORD_BLANK.getKey()));
        }

        if (!errors.isEmpty()) {
            throw new UserInputValidationException(errors);
        }
    }
}
