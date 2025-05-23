package com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.AssignedDoctorData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO to transfer data of Get doctors by patient id api
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignedDoctorsToPatientDto {
    private String id;
    private String firstName;
    private String lastName;
    private List<AssignedDoctorData> doctors;
}
