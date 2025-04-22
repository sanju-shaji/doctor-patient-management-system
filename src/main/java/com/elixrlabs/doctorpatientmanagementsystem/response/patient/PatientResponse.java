package com.elixrlabs.doctorpatientmanagementsystem.response.patient;

import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Data transfer object for transferring the patient data
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PatientResponse extends BaseResponse {
    private Object data;
    private List<String> patients;
}
