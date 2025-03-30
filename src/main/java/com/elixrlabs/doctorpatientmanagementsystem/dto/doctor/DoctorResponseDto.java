package com.elixrlabs.doctorpatientmanagementsystem.dto.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Data transfer object for POST/doctor API
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class DoctorResponseDto extends ResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String department;

    /**
     * constructor to set PostDoctorDto fields
     */
    public DoctorResponseDto(DoctorEntity doctorEntity) {
        setId(doctorEntity.getId());
        setFirstName(doctorEntity.getFirstName());
        setLastName(doctorEntity.getLastName());
        setDepartment(doctorEntity.getDepartment());
    }
}
