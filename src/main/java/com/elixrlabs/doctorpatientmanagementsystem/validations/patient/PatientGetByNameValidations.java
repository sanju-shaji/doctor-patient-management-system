package com.elixrlabs.doctorpatientmanagementsystem.validations.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.PatientGetByNameConstants;

import java.util.ArrayList;
import java.util.List;
/**
 * class to validate GetByName/Patient module variables
 */
public class PatientGetByNameValidations {
    private static final List<String> ERROR_MESSAGES = List.of(PatientGetByNameConstants.ONLY_ALPHABETS,
            PatientGetByNameConstants.UNSUPPORTED_CHARACTERS, PatientGetByNameConstants.ONLY_ALPHABETS,
            PatientGetByNameConstants.INVALID_CHARACTERS);

    public static List<String> validatePatientName(String name) {
        List<String> errors = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            errors.add(ERROR_MESSAGES.get(0));
        }
        if (!name.matches(PatientGetByNameConstants.REGEX_PPATTERN)) {
            errors.add(ERROR_MESSAGES.get(1));
        }
        if (name.length() < 2 || name.length() > 50) {
            errors.add(ERROR_MESSAGES.get(2));
        }
        if (name.contains(PatientGetByNameConstants.APOSTROPHE) || name.contains(PatientGetByNameConstants.HYPHEN)) {
            errors.add(ERROR_MESSAGES.get(3));
        }
        return errors;
    }
}
