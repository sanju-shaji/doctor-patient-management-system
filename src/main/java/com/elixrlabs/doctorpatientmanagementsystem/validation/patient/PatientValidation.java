package com.elixrlabs.doctorpatientmanagementsystem.validation.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DoctorPatientManagementSystemConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.RequestDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validation class for validating patient details
 */
@Component
public class PatientValidation {
    private final Pattern NAME_PATTERN = Pattern.compile(DoctorPatientManagementSystemConstants.REGEX_PATIENT_NAME_PATTERN);

    /**
     * Validates the patient details based on pre-defined rules
     * This method ensures that all required fields are present and meet the validation criteria
     *
     * @param patientDto DTO object containing patient details for validation
     * @return a list of error messages if validation fails and an empty list if validation passes
     */
    public List<String> validatePatient(RequestDto patientDto) {
        List<String> errors = new ArrayList<>();
        String patientFirstName = patientDto.getPatientFirstName() != null ? patientDto.getPatientFirstName().trim() : "";
        String patientLastName = patientDto.getPatientLastName() != null ? patientDto.getPatientLastName().trim() : "";
        if (patientDto.getPatientFirstName().isEmpty()) {
            errors.add(DoctorPatientManagementSystemConstants.EMPTY_PATIENT_FIRSTNAME_ERROR);
        } else if (!NAME_PATTERN.matcher(patientFirstName).matches()) {
            errors.add(DoctorPatientManagementSystemConstants.PATIENT_FIRSTNAME_PATTERN_ERROR);
        }
        if (patientDto.getPatientLastName().isEmpty()) {
            errors.add(DoctorPatientManagementSystemConstants.EMPTY_PATIENT_LASTNAME_ERROR);
        } else if (!NAME_PATTERN.matcher(patientLastName).matches()) {
            errors.add(DoctorPatientManagementSystemConstants.PATIENT_LASTNAME_PATTERN_ERROR);
        }
        return errors;
    }
}
