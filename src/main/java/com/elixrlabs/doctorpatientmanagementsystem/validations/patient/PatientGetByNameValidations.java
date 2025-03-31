package com.elixrlabs.doctorpatientmanagementsystem.validations.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.PatientGetByNameConstants;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * class to validate GetByName/Patient module variables
 */
@Component
public class PatientGetByNameValidations {
    public List<String> validatePatientName(String name) {
        List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(name)) {
            errors.add(PatientGetByNameConstants.NAME_CANNOT_NULL);
        }
        if (!name.matches(PatientGetByNameConstants.REGEX_PATTERN)) {
            errors.add(PatientGetByNameConstants.NAME_UNSUPPORTED_CHARACTERS);
        }
        if (name.length() < 2 || name.length() > 50) {
            errors.add(PatientGetByNameConstants.NAME_LENGTH);
        }
        if (name.contains(PatientGetByNameConstants.APOSTROPHE) || name.contains(PatientGetByNameConstants.HYPHEN)) {
            errors.add(PatientGetByNameConstants.NAME_INVALID_CHARACTERS);
        }
        return errors;
    }
}
