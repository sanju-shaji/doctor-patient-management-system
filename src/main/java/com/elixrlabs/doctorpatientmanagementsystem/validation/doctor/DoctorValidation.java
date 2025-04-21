package com.elixrlabs.doctorpatientmanagementsystem.validation.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidAssignmentDataException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.UuidValidationException;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validator class for validating doctor name input.
 */
@RequiredArgsConstructor
@Component
public class DoctorValidation {
    private final MessageUtil messageUtil;
    private final Pattern UUID_PATTERN = Pattern.compile(ApplicationConstants.REGEX_UUID_PATTERN);

    /**
     * method to check if the string is empty and if string follows the specified pattern
     * Validates the doctor name to ensure it is not empty and contains only letters and spaces.
     *
     * @param string              The string which is to be validated
     * @param Pattern             The pattern which is to be matched with the string for validation
     * @param emptyStringError    error message if the string is empty
     * @param invalidPatternError error message if the string does for match the specified pattern
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
     */
    public void validateDoctorDetails(DoctorDto doctor) throws InvalidUserInputException {
        validateString(doctor.getFirstName(),
                ApplicationConstants.REGEX_ALPHABET_PATTERN, messageUtil.getMessage(MessageKeyEnum.EMPTY_FIRSTNAME.getKey()),
                messageUtil.getMessage(MessageKeyEnum.FIRSTNAME_PATTERN_ERROR.getKey()));
        validateString(doctor.getLastName(),
                ApplicationConstants.REGEX_ALPHABET_PATTERN, messageUtil.getMessage(MessageKeyEnum.EMPTY_LASTNAME.getKey()),
                messageUtil.getMessage(MessageKeyEnum.LASTNAME_PATTERN_ERROR.getKey()));
        validateString(doctor.getDepartment(),
                ApplicationConstants.REGEX_DEPARTMENTNAME_PATTERN, messageUtil.getMessage(MessageKeyEnum.EMPTY_DEPARTMENT_NAME.getKey()),
                messageUtil.getMessage(MessageKeyEnum.DEPARTMENT_NAME_PATTERN_ERROR.getKey()));
    }

    /**
     * method to check if user is not providing a valid UUID
     *
     * @param id-UUID
     * @return True if id is valid else false
     */
    public boolean isInValidUUID(String id) {
        return !UUID_PATTERN.matcher(id).matches();
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
     */
    public void validateDoctorId(String doctorId) {
        List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(doctorId)) {
            String message = messageUtil.getMessage(MessageKeyEnum.UUID_NOT_PRESENT.getKey(), ApplicationConstants.DOCTOR);
            errors.add(message);
        } else if (isInValidUUID(doctorId)) {
            String message = messageUtil.getMessage(MessageKeyEnum.DOCTOR_INVALID_UUID_FORMAT.getKey(), ApplicationConstants.DOCTOR);
            errors.add(message);
        }
        if (!errors.isEmpty()) {
            throw new InvalidAssignmentDataException(errors);
        }
    }

    /**
     * Validates the UUID format and presence of both doctorId and patientId.
     * This method checks if the provided doctor and patient IDs are non-blank and follow the standard UUID format.
     * If either ID is missing or invalid, corresponding error messages are collected
     */
    public void validateDoctorAndPatientIds(String doctorId, String patientId) {
        List<String> errors = new ArrayList<>();

        if (StringUtils.isBlank(doctorId)) {
            String message = messageUtil.getMessage(MessageKeyEnum.UUID_NOT_PRESENT.getKey(), ApplicationConstants.DOCTOR);
            errors.add(message);
        } else if (isInValidUUID(doctorId)) {
            String message = messageUtil.getMessage(MessageKeyEnum.DOCTOR_INVALID_UUID_FORMAT.getKey(), ApplicationConstants.DOCTOR);
            errors.add(message);
        }
        if (StringUtils.isBlank(patientId)) {
            String msg = messageUtil.getMessage(MessageKeyEnum.UUID_NOT_PRESENT.getKey(), ApplicationConstants.PATIENT);
            errors.add(msg);
        } else if (isInValidUUID(patientId)) {
            String msg = messageUtil.getMessage(MessageKeyEnum.DOCTOR_INVALID_UUID_FORMAT.getKey(), ApplicationConstants.PATIENT);
            errors.add(msg);
        }
        if (!errors.isEmpty()) {
            throw new UuidValidationException(errors);
        }
    }
}
