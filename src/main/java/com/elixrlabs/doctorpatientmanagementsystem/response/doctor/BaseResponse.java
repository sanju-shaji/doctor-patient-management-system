package com.elixrlabs.doctorpatientmanagementsystem.response.doctor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Base response class for doctor-related API responses. *
 */
@Getter
@Setter
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BaseResponse {
    private boolean success;
    private List<String> errors;
}
