package com.elixrlabs.doctorpatientmanagementsystem.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response object for authentication requests containing success status, token, or error message.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {

    private boolean success;
    private String token;
    private String errors;

    /**
     * Constructs an AuthResponse with success status and JWT token.
     */
    public AuthResponse(Boolean success, String token) {
        this.setSuccess(success);
        this.setToken(token);
    }
}
