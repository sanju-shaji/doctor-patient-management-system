package com.elixrlabs.doctorpatientmanagementsystem.validation.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DoctorPatientManagementSystemConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.RequestDto;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        String firstName = patientDto.getFirstName().trim();
        String lastName = patientDto.getLastName().trim();
        if (StringUtils.isEmpty(firstName)) {
            errors.add(DoctorPatientManagementSystemConstants.PATIENT_FIRSTNAME_ERROR);
        } else if (!NAME_PATTERN.matcher(patientDto.getFirstName()).matches()) {
            errors.add(DoctorPatientManagementSystemConstants.PATIENT_FIRSTNAME_PATTERN_ERROR);
        }
        if (StringUtils.isEmpty(lastName)) {
            errors.add(DoctorPatientManagementSystemConstants.PATIENT_LASTNAME_ERROR);
        } else if (!NAME_PATTERN.matcher(patientDto.getLastName()).matches()) {
            errors.add(DoctorPatientManagementSystemConstants.PATIENT_LASTNAME_PATTERN_ERROR);
        }
        return errors;
    }
}
