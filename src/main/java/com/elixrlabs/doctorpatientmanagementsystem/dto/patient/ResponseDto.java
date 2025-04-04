package com.elixrlabs.doctorpatientmanagementsystem.dto.patient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * DTO class for API responses
 * Contains success status, patient details and error messages
 */
public class ResponseDto {
    private boolean success;
    private Object data;
    private List<String> errors;
}
