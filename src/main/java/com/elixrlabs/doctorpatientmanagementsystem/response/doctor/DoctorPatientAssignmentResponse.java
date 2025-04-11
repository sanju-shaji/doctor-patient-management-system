package com.elixrlabs.doctorpatientmanagementsystem.response.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Response object of get doctors by patient id api
 */
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorPatientAssignmentResponse extends BaseResponse {
    private String id;
    private String firstName;
    private String lastName;
    private List<DoctorDto> doctors;
}
