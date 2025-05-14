package com.elixrlabs.doctorpatientmanagementsystem.rest.controller;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserNameOrPasswordException;
import com.elixrlabs.doctorpatientmanagementsystem.model.AuthRequest;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.JwtUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    MessageUtil messageUtil;

    @PostMapping(ApiConstants.AUTHENTICATE_API)
    public BaseResponse generateToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
            String token =jwtUtil.generateToken(authRequest.getUserName());
            return new BaseResponse(token,true);

        } catch (Exception exception) {
           String message = messageUtil.getMessage(MessageKeyEnum.INVALID_USERNAME_OR_PASSWORD.getKey());
            throw new InvalidUserNameOrPasswordException(message);
        }
    }
}
