package com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

/**
 * Response object of get doctors by patient id api
 */
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorPatientAssignmentResponse extends BaseResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private List<AssignedDoctorData> doctors;
}
