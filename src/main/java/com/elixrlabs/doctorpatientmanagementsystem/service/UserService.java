package com.elixrlabs.doctorpatientmanagementsystem.service;

import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.UserAlreadyExistsException;
import com.elixrlabs.doctorpatientmanagementsystem.model.UsersModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.UserRepository;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageUtil messageUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, MessageUtil messageUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageUtil = messageUtil;
    }

    public void registerUser(UsersModel usersModel) {
        if (userRepository.findByUserName(usersModel.getUsername()).isPresent()) {
            String message = messageUtil.getMessage(MessageKeyEnum.USER_NAME_ALREADY_EXIT.getKey());
            throw new UserAlreadyExistsException(message);
        }
        usersModel.setUuid(UUID.randomUUID());
        usersModel.setPassword(passwordEncoder.encode(usersModel.getPassword()));
        userRepository.save(usersModel);
    }
}
