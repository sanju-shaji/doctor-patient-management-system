package com.elixrlabs.doctorpatientmanagementsystem.validation.doctor;


import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Validator class for validating doctor name input.
 */
@Component
public class DoctorValidation {
    /**
     * method to check if the string is empty and if string follows the specified pattern
     *
     * @param string-The                string which is to be validated
     * @param Pattern-The               pattern which is to be matched with the string for validation
     * @param emptyStringError-error    message if the string is empty
     * @param invalidPatternError-error message if the string does for match the specified pattern
     * @return List which contains the error messages if validation fails or an empty list if validation is success
     * Validates the doctor name to ensure it is not empty and contains only letters and spaces.
     */
    public List<String> validateString(String string, String Pattern,
                                       String emptyStringError, String invalidPatternError) {
        List<String> errorMessageList = new ArrayList<>();
        if (StringUtils.isBlank(string)) {
            errorMessageList.add(emptyStringError);
            return errorMessageList;
        }
        if (!string.matches(Pattern)) {
            errorMessageList.add(invalidPatternError);
            return errorMessageList;
        }
        return errorMessageList;
    }

    /**
     * method to validate the POST/doctor Api
     *
     * @param doctor-model entity which contains the actual data
     * @return list which contains error messages if any
     */
    public List<String> validatePostDoctor(DoctorDto doctor) {
        List<String> errorMessageList = new ArrayList<>();
        errorMessageList.addAll(validateString(doctor.getFirstName(),
                ApplicationConstants.REGEX_ALPHABET_PATTERN, ApplicationConstants.EMPTY_FIRSTNAME,
                ApplicationConstants.FIRSTNAME_PATTERN_ERROR));
        errorMessageList.addAll(validateString(doctor.getLastName(),
                ApplicationConstants.REGEX_ALPHABET_PATTERN, ApplicationConstants.EMPTY_LASTNAME,
                ApplicationConstants.LASTNAME_PATTERN_ERROR));
        errorMessageList.addAll(validateString(doctor.getDepartment(),
                ApplicationConstants.REGEX_DEPARTMENTNAME_PATTERN, ApplicationConstants.EMPTY_DEPARTMENTNAME,
                ApplicationConstants.DEPARTMENTNAME_PATTERN_ERROR));
        return errorMessageList;

    }

    public Boolean validateDoctorName(String name) {
        return StringUtils.isBlank(name);
    }
}