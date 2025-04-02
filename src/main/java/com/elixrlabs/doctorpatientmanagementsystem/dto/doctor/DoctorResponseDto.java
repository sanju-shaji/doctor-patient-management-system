package com.elixrlabs.doctorpatientmanagementsystem.dto.doctor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Data transfer object for transferring the doctor data
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@NoArgsConstructor
public class DoctorResponseDto extends ResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String department;
}
