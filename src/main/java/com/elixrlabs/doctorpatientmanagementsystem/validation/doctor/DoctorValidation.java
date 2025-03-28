package com.elixrlabs.doctorpatientmanagementsystem.validation.doctor;

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
        String alphabetPattern = "^[a-zA-Z\\s]+$";
        return string.matches(alphabetPattern);
    }

    /**
     * method to check if department name contains any restricted special symbols
     *
     * @param string
     * @return
     */
    public boolean isValidDepartmentName(String string) {
        String departmentNamePattern = "^[a-zA-Z\\s\\-'.,]+$";
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
            errorMessageList.add("First name should not be an empty string");
        }
        if (!containsOnlyAlbhabets(doctor.getFirstName())) {
            errorMessageList.add("First name should only contain alphabets");
        }
        if (isEmptyString(doctor.getLastName())) {
            errorMessageList.add("Last name should not be an empty string");
        }
        if (!containsOnlyAlbhabets(doctor.getLastName())) {
            errorMessageList.add("Last name should only contain alphabets");
        }
        if (isEmptyString(doctor.getDepartment())) {
            errorMessageList.add("Department should not be empty");
        }
        if (!isValidDepartmentName(doctor.getDepartment())) {
            errorMessageList.add("Invalid Department Name");
        }
        return errorMessageList;
    }
}
