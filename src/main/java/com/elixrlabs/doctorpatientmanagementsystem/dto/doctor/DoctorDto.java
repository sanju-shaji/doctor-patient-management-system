package com.elixrlabs.doctorpatientmanagementsystem.dto.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO representing doctor details for data transfer.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String department;

    public DoctorDto(DoctorEntity doctorEntity) {

        this.id = doctorEntity.getId();
        this.firstName = doctorEntity.getFirstName();
        this.lastName = doctorEntity.getLastName();
        this.department = doctorEntity.getDepartment();
    }
}
