package com.elixrlabs.doctorpatientmanagementsystem.validation.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DPMSConstants;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * class to validate doctor module variables
 */
@Component
public class DoctorValidation {
    /**
     * method to check if string is empty
     */
    public boolean isEmptyString(String string) {
        return string.isEmpty();
    }

    /**
     * method to check if string contains only alphabets
     */
    public boolean containsOnlyAlbhabets(String string) {
        String alphabetPattern = DPMSConstants.REGEX_ALPHABET_PATTERN;
        return string.matches(alphabetPattern);
    }

    /**
     * method to check if department name contains any restricted special symbols
     */
    public boolean isValidDepartmentName(String string) {
        String departmentNamePattern = DPMSConstants.REGEX_DEPARTMENTNAME_PATTERN;
        return string.matches(departmentNamePattern);
    }
    public boolean isValidUUID(String id){
        try {
            UUID.fromString(id);
            return true;
        }catch (IllegalArgumentException illegalArgumentException){
            return false;
        }
    }

    public boolean isUUIDNull(String id){
        return StringUtils.isBlank(id);
    }

    /**
     * method to validate the POST/doctor Api
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
