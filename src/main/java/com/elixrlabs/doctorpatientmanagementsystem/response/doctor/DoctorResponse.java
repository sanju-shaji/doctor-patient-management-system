package com.elixrlabs.doctorpatientmanagementsystem.response.doctor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
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
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DoctorResponse extends BaseResponse{
    private UUID id;
    private String firstName;
    private String lastName;
    private String department;
}
