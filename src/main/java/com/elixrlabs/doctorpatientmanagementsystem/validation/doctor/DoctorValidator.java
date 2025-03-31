package com.elixrlabs.doctorpatientmanagementsystem.validation.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ErrorConstants;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/** Validator class for validating doctor name input. */
@Component
public class DoctorValidator {

    /** Validates the doctor name to ensure it is not empty and contains only letters and spaces. */
    public List<String> validateDoctorName(String name) {
        List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(name)) {
            errors.add(ErrorConstants.NOT_EMPTY_ERROR_MESSAGE);
        } else if (!name.matches(ErrorConstants.REGULAR_EXPRESSION_MESSAGE)) {
            errors.add(ErrorConstants.INVALID_EXPRESSION_ERROR_MESSAGE);
        }
        return errors;
    }
}
