package com.elixrlabs.doctorpatientmanagementsystem.rest.controller;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserNameOrPasswordException;
import com.elixrlabs.doctorpatientmanagementsystem.model.AuthRequest;
import com.elixrlabs.doctorpatientmanagementsystem.model.UsersModel;
import com.elixrlabs.doctorpatientmanagementsystem.service.UserService;
import com.elixrlabs.doctorpatientmanagementsystem.util.JWTUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    MessageUtil messageUtil;

    @PostMapping(ApiConstants.AUTHENTICATE_API)
    public String generateToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
            return jwtUtil.generateToken(authRequest.getUserName());
        } catch (Exception exception) {
           String message = messageUtil.getMessage(MessageKeyEnum.INVALID_USERNAME_OR_PASSWORD.getKey());
            throw new InvalidUserNameOrPasswordException(message);
        }
    }
}
