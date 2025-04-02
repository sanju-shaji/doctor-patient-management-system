package com.elixrlabs.doctorpatientmanagementsystem.response.doctor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * Base response class for doctor-related API responses. *
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse {
    public boolean success;
    public String error;
}
