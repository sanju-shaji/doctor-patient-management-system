package com.elixrlabs.doctorpatientmanagementsystem.rest.controller;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.UsersDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.model.UsersModel;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.UserService;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.validation.UserNameAndPasswordValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling user registration requests.
 */
@RestController
public class UserRegisterController {

    private final UserService userService;
    private final MessageUtil messageUtil;
    private final UserNameAndPasswordValidator userNameAndPasswordValidator;

    public UserRegisterController(UserService userService, MessageUtil messageUtil, UserNameAndPasswordValidator userNameAndPasswordValidator) {
        this.userService = userService;
        this.messageUtil = messageUtil;
        this.userNameAndPasswordValidator = userNameAndPasswordValidator;
    }

    /**
     * Validates and registers a new user.
     */
    @PostMapping(ApiConstants.REGISTER_END_POINT)
    public BaseResponse saveUser(@RequestBody UsersModel usersModel) {
        userNameAndPasswordValidator.validateUserNameAndPassword(new UsersDto(usersModel));
        userService.registerUser(usersModel);
        return new BaseResponse(messageUtil.getMessage(MessageKeyEnum.USER_REGISTRATION_SUCCESS.getKey()), true);
    }
}
