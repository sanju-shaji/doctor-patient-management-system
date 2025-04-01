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
            errors.add(PatientGetByNameConstants.QUERY_PARAMS_CANNOT_NULL);
        }
        return errors;
    }
}
