package com.elixrlabs.doctorpatientmanagementsystem.dto.doctor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Data transfer object for transferring the doctor data
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder

public class  DoctorDto extends ResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String department;
}
