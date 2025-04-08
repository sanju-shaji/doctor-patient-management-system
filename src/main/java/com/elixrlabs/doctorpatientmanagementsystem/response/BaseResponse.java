package com.elixrlabs.doctorpatientmanagementsystem.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
/**
 * DTO class for API responses
 * Contains success status, patient details and error messages
 */
public class BaseResponse {
    private boolean success;
    private List<String> errors;
}
