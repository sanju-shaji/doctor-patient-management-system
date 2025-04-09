package com.elixrlabs.doctorpatientmanagementsystem.validation.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.EmptyUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidExcetion;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * Validator class for validating doctor name input.
 */
@Component
public class DoctorValidation {

    /**
     * method to check if the string is empty and if string follows the specified pattern
     *
     * @param string              The string which is to be validated
     * @param Pattern             The pattern which is to be matched with the string for validation
     * @param emptyStringError    error message if the string is empty
     * @param invalidPatternError error message if the string does for match the specified pattern
     * @return List which contains the error messages if validation fails or an empty list if validation is success
     * Validates the doctor name to ensure it is not empty and contains only letters and spaces.
     */
    public void validateString(String string, String Pattern,
                               String emptyStringError, String invalidPatternError) throws InvalidUserInputException {
        if (StringUtils.isBlank(string)) {
            throw new InvalidUserInputException(emptyStringError);
        }
        if (!string.matches(Pattern)) {
            throw new InvalidUserInputException(invalidPatternError);
        }
    }

    /**
     * method to validate the POST/doctor Api
     *
     * @param doctor-model entity which contains the actual data
     * @return list which contains error messages if any
     */
    public void validateDoctorDetails(DoctorDto doctor) throws InvalidUserInputException {
        validateString(doctor.getFirstName(),
                ApplicationConstants.REGEX_ALPHABET_PATTERN, ApplicationConstants.EMPTY_FIRSTNAME,
                ApplicationConstants.FIRSTNAME_PATTERN_ERROR);
        validateString(doctor.getLastName(),
                ApplicationConstants.REGEX_ALPHABET_PATTERN, ApplicationConstants.EMPTY_LASTNAME,
                ApplicationConstants.LASTNAME_PATTERN_ERROR);
        validateString(doctor.getDepartment(),
                ApplicationConstants.REGEX_DEPARTMENTNAME_PATTERN, ApplicationConstants.EMPTY_DEPARTMENTNAME,
                ApplicationConstants.DEPARTMENTNAME_PATTERN_ERROR);
    }

    /**
     * method to check if user is not providing a valid UUID
     *
     * @param id-UUID
     * @return True if id is valid else false
     */
    public boolean isValidUUID(String id) {
        try {
            UUID.fromString(id);
            return true;
        } catch (IllegalArgumentException illegalArgumentException) {
            return false;
        }
    }

    /**
     * This method represents if user entered the doctorName in the url or not
     *
     * @param doctorName to validate the user enter the doctorName in the url or not
     * @return it will return true if user doesn't enter any name in the url or false if user entered the doctorName
     */
    public boolean validateDoctorName(String doctorName) {
        return StringUtils.isBlank(doctorName);
    }

    /**
     * this method checks if the given doctor ID is valid or not
     * if the ID is missing or not a valid UUID ,it adds errors messages.
     *
     * @param doctorId to validate the  doctorId
     * @return responseEntity containing error messages if validation fails or null if ID is valid
     */
    public void validatePatchDoctor(String doctorId) throws InvalidUuidExcetion, EmptyUuidException {
        if (StringUtils.isBlank(doctorId)) {
            throw new EmptyUuidException(ApplicationConstants.MISSING_ID);
        }
        if (!StringUtils.isBlank(doctorId)) {
            try {
                UUID.fromString(doctorId);
            } catch (NullPointerException | IllegalArgumentException e) {
                throw new InvalidUuidExcetion(ApplicationConstants.INVALID_UUID);

            }
        }
    }

}
