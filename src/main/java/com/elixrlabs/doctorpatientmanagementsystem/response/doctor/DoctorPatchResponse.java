package com.elixrlabs.doctorpatientmanagementsystem.response.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response class for returning a updated doctor data.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorPatchResponse extends BaseResponse {
    private DoctorDto doctor;
}
