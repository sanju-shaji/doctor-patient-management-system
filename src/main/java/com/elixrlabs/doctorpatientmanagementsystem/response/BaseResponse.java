package com.elixrlabs.doctorpatientmanagementsystem.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.List;

/**
 * Base response class for patient-related API responses. *
 */
@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class BaseResponse {
    private boolean success;
    private List<String> messages;
    private List<String> errors;
    private Object data;

    public BaseResponse(String message,Boolean success) {
        this.setSuccess(success);
        this.setMessages(Collections.singletonList(message));
    }
}
