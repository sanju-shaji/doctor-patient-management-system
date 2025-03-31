package com.elixrlabs.doctorpatientmanagementsystem.response.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/** Base response class for doctor-related API responses. */
@Getter
@Setter
public class BaseResponse {
    public boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<DoctorDto> doctors;
}
