package com.elixrlabs.doctorpatientmanagementsystem.dto.doctor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Base DTO for doctor class for setting success and error
 */
@Getter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ResponseDto {
    private boolean success;
    private List<String> errors;
}
