package com.elixrlabs.doctorpatientmanagementsystem.validation.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.EmptyUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidExcetion;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Validation class for validating patient details
 */
@Component
public class PatientValidation {
    private final Pattern NAME_PATTERN = Pattern.compile(ApplicationConstants.REGEX_PATIENT_NAME_PATTERN);
    private final MessageUtil messageUtil;
    public PatientValidation(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    /**
     * Validates the patient details based on pre-defined rules
     * This method ensures that all required fields are present and meet the validation criteria
     *
     * @param patientDto DTO object containing patient details for validation
     * @return a list of error messages if validation fails and an empty list if validation passes
     */
    public List<String> validatePatient(PatientDto patientDto) {
        List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(patientDto.getFirstName())) {
            errors.add(ApplicationConstants.PATIENT_FIRSTNAME_ERROR);
        } else if (!NAME_PATTERN.matcher(patientDto.getFirstName()).matches()) {
            errors.add(ApplicationConstants.PATIENT_FIRSTNAME_PATTERN_ERROR);
        }
        if (StringUtils.isEmpty(patientDto.getLastName())) {
            errors.add(ApplicationConstants.PATIENT_LASTNAME_ERROR);
        } else if (!NAME_PATTERN.matcher(patientDto.getLastName()).matches()) {
            errors.add(ApplicationConstants.PATIENT_LASTNAME_PATTERN_ERROR);
        }
        return errors;
    }

    public List<String> validatePatientName(String name) {
        List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(name)) {
            errors.add(ApplicationConstants.QUERY_PARAMS_CANNOT_NULL);
        }
        return errors;
    }

    public void validatePatientId(String id) throws EmptyUuidException, InvalidUuidExcetion {
        if (StringUtils.isBlank(id)) {
            String message= messageUtil.getMessage(MessageKeyEnum.BLANK_UUID,null);
           throw new EmptyUuidException(message);
        }
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException illegalArgumentException) {
            String message=messageUtil.getMessage(MessageKeyEnum.INVALID_UUID_FORMAT,null);
            throw new InvalidUuidExcetion(message);
        }
    }
}
