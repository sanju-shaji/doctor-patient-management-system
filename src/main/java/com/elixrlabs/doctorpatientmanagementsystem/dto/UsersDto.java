package com.elixrlabs.doctorpatientmanagementsystem.dto;

import com.elixrlabs.doctorpatientmanagementsystem.model.UsersModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for transferring user credentials.
 */
@Getter
@Setter
@AllArgsConstructor
public class UsersDto {

    private String userName;
    private String password;

    /**
     * Constructs a UsersDto from a UsersModel entity.
     */
    public UsersDto(UsersModel usersModel) {
        this.userName = usersModel.getUsername();
        this.password = usersModel.getPassword();
    }
}
