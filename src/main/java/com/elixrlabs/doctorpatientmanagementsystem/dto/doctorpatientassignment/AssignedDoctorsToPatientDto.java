package com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * DTO to transfer data of Get doctors by patient id api
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AssignedDoctorsToPatientDto {
    private String id;
    private String firstName;
    private String lastName;
    private List<DoctorDto> doctors;
}
