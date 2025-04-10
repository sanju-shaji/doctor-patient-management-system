package com.elixrlabs.doctorpatientmanagementsystem.dto.doctor;

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
public class DPADto {
    private String id;
    private String firstName;
    private String lastName;
    private List<DoctorDto> doctors;
}
