package com.elixrlabs.doctorpatientmanagementsystem.service;

import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserNameOrPasswordException;
import com.elixrlabs.doctorpatientmanagementsystem.repository.UserRepository;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final MessageUtil messageUtil;

    public CustomUserDetailsService(UserRepository userRepository, MessageUtil messageUtil) {
        this.userRepository = userRepository;
        this.messageUtil = messageUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        return userRepository.findByUserName(username)
                .orElseThrow(() ->
                        new InvalidUserNameOrPasswordException(messageUtil.getMessage(MessageKeyEnum.INVALID_USERNAME_OR_PASSWORD.getKey())));
    }
}
