package com.elixrlabs.doctorpatientmanagementsystem.response.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Response class for returning a list of doctors.
 * Extends {@link BaseResponse} to include common response attributes.
 * Uses {@link JsonInclude} to exclude null values from the JSON response.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class DoctorListResponse extends BaseResponse {
    private List<DoctorDto> doctors;
}
