package com.elixrlabs.doctorpatientmanagementsystem.validation.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DPMSConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PostPatientDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validation class for validating patient details
 */
@Component
public class PatientValidation {
    private final Pattern NAME_PATTERN = Pattern.compile(DPMSConstants.REGEX_PATIENT_NAME_PATTERN);

    /**
     * Validates patient first name, last name
     *
     * @param patientDto
     * @return
     */
    public List<String> validatePatient(PostPatientDto patientDto) {
        List<String> errors = new ArrayList<>();
        String patientFirstName = patientDto.getPatientFirstName() != null ? patientDto.getPatientFirstName().trim() : "";
        String patientLastName = patientDto.getPatientLastName() != null ? patientDto.getPatientLastName().trim() : "";
        if (patientDto.getPatientFirstName().isEmpty()) {
            errors.add(DPMSConstants.EMPTY_PATIENT_FIRSTNAME_ERROR);
        } else if (!NAME_PATTERN.matcher(patientFirstName).matches()) {
            errors.add(DPMSConstants.PATIENT_FIRSTNAME_PATTERN_ERROR);
        }
        if (patientDto.getPatientLastName().isEmpty()) {
            errors.add(DPMSConstants.EMPTY_PATIENT_LASTNAME_ERROR);
        } else if (!NAME_PATTERN.matcher(patientLastName).matches()) {
            errors.add(DPMSConstants.PATIENT_LASTNAME_PATTERN_ERROR);
        }
        return errors;
    }
}
