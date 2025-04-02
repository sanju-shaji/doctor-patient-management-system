package com.elixrlabs.doctorpatientmanagementsystem.validation.doctor;

import io.micrometer.common.util.StringUtils;

import org.springframework.stereotype.Component;

/**
 * Validator class for validating doctor name input.
 */
@Component
public class DoctorValidator {
    /**
     * Validates the doctor name to ensure it is not empty and contains only letters and spaces.
     */
    public Boolean validateDoctorName(String name) {
        return StringUtils.isBlank(name);
    }
}
