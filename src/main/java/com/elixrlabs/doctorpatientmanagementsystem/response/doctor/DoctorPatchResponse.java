package com.elixrlabs.doctorpatientmanagementsystem.response.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

/**
 * Response class for returning a updated doctor data.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Component
@SuperBuilder
@NoArgsConstructor
public class DoctorPatchResponse extends BaseResponse {
    private DoctorDto doctor;
}
