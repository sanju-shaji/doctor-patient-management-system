package com.elixrlabs.doctorpatientmanagementsystem.validation.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * class to validate doctor module variables
 */
@Component
public class DoctorValidation {
    /**
     * method to check if string is empty or null
     *
     * @param string which is to be validated
     * @return true or false
     */
    public boolean isEmptyString(String string) {
        return StringUtils.isBlank(string);
    }

    /**
     * method to check if string contains only alphabets
     *
     * @param string for validating with regex patter
     * @return true or false
     */
    public boolean containsOnlyAlphabets(String string) {
        String alphabetPattern = ApplicationConstants.REGEX_ALPHABET_PATTERN;
        return string.matches(alphabetPattern);
    }

    /**
     * method to check if department name contains any restricted special symbols
     *
     * @param string for validating with regex patter
     * @return true or false
     */
    public boolean isValidDepartmentName(String string) {
        String departmentNamePattern = ApplicationConstants.REGEX_DEPARTMENTNAME_PATTERN;
        return string.matches(departmentNamePattern);
    }

    /**
     * method to validate the POST/doctor Api
     *
     * @param doctor-model entity which contains the actual data
     * @return list which contains error messages if any
     */
    public List<String> validatePostDoctor(DoctorEntity doctor) {
        List<String> errorMessageList = new ArrayList<>();
        if (isEmptyString(doctor.getFirstName())) {
            errorMessageList.add(ApplicationConstants.EMPTY_FIRSTNAME);
        }
        if (!isEmptyString(doctor.getFirstName())) {
            if (!containsOnlyAlphabets(doctor.getFirstName())) {
                errorMessageList.add(ApplicationConstants.FIRSTNAME_PATTERN_ERROR);
            }
        }
        if (isEmptyString(doctor.getLastName())) {
            errorMessageList.add(ApplicationConstants.EMPTY_LASTNAME);
        }
        if (!isEmptyString(doctor.getLastName())) {
            if (!containsOnlyAlphabets(doctor.getLastName())) {
                errorMessageList.add(ApplicationConstants.LASTNAME_PATTERN_ERROR);
            }
        }
        if (isEmptyString(doctor.getDepartment())) {
            errorMessageList.add(ApplicationConstants.EMPTY_DEPARTMENTNAME);
        }
        if (!isValidDepartmentName(doctor.getDepartment())) {
            errorMessageList.add(ApplicationConstants.DEPARTMENTNAME_PATTERN_ERROR);
        }
        return errorMessageList;
    }
}
