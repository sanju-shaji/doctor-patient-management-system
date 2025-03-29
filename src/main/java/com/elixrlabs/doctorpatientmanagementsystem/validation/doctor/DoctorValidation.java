package com.elixrlabs.doctorpatientmanagementsystem.validation.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DPMSConstants;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * class to validate doctor module variables
 */
public class DoctorValidation {
    /**
     * method to check if string is empty
     *
     * @param string
     * @return
     */
    public boolean isEmptyString(String string) {
        return string.isEmpty();
    }

    /**
     * method to check if string contains only alphabets
     *
     * @param string
     * @return
     */
    public boolean containsOnlyAlbhabets(String string) {
        String alphabetPattern = DPMSConstants.REGEX_ALPHABET_PATTERN;
        return string.matches(alphabetPattern);
    }

    /**
     * method to check if department name contains any restricted special symbols
     *
     * @param string
     * @return
     */
    public boolean isValidDepartmentName(String string) {
        String departmentNamePattern = DPMSConstants.REGEX_DEPARTMENTNAME_PATTERN;
        return string.matches(departmentNamePattern);
    }

    /**
     * method to validate the POST/doctor Api
     *
     * @param doctor
     * @return
     */
    public List<String> validatePostDoctor(DoctorEntity doctor) {
        List<String> errorMessageList = new ArrayList<>();
        if (isEmptyString(doctor.getFirstName())) {
            errorMessageList.add(DPMSConstants.EMPTY_FIRSTNAME);
        }
        if (!containsOnlyAlbhabets(doctor.getFirstName())) {
            errorMessageList.add(DPMSConstants.FIRSTNAME_PATTERN_ERROR);
        }
        if (isEmptyString(doctor.getLastName())) {
            errorMessageList.add(DPMSConstants.EMPTY_LASTNAME);
        }
        if (!containsOnlyAlbhabets(doctor.getLastName())) {
            errorMessageList.add(DPMSConstants.LASTNAME_PATTERN_ERROR);
        }
        if (isEmptyString(doctor.getDepartment())) {
            errorMessageList.add(DPMSConstants.EMPTY_DEPARTMENTNAME);
        }
        if (!isValidDepartmentName(doctor.getDepartment())) {
            errorMessageList.add(DPMSConstants.DEPARTMENTNAME_PATTERN_ERROR);
        }
        return errorMessageList;
    }
}
