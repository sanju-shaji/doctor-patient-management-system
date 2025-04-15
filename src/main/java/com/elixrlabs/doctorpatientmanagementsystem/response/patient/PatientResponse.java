package com.elixrlabs.doctorpatientmanagementsystem.response.patient;

import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Data transfer object for transferring the patient data
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@NoArgsConstructor
public class PatientResponse extends BaseResponse {
    private Object data;
}
