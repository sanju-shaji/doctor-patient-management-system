package com.elixrlabs.doctorpatientmanagementsystem.validation.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidException;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validation class for validating patient details
 */
@Component
public class PatientValidation {
    private final Pattern NAME_PATTERN = Pattern.compile(ApplicationConstants.REGEX_PATIENT_NAME_PATTERN);
    private final Pattern UUID_PATTERN = Pattern.compile(ApplicationConstants.REGEX_UUID_PATTERN);
    private final MessageUtil messageUtil;

    public PatientValidation(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    /**
     * Validates the patient details based on pre-defined rules
     * This method ensures that all required fields are present and meet the validation criteria
     *
     * @param patient DTO object containing patient details for validation
     */
    public void validatePatient(PatientDto patient) throws InvalidUserInputException {
        if (StringUtils.isBlank(patient.getFirstName())) {
            throw new InvalidUserInputException(messageUtil.getMessage(MessageKeyEnum.PATIENT_FIRSTNAME_ERROR.getKey()));
        } else if (!NAME_PATTERN.matcher(patient.getFirstName()).matches()) {
            throw new InvalidUserInputException(messageUtil.getMessage(MessageKeyEnum.PATIENT_FIRSTNAME_PATTERN_ERROR.getKey()));
        }
        if (StringUtils.isBlank(patient.getLastName())) {
            throw new InvalidUserInputException(messageUtil.getMessage(MessageKeyEnum.PATIENT_LASTNAME_ERROR.getKey()));
        } else if (!NAME_PATTERN.matcher(patient.getLastName()).matches()) {
            throw new InvalidUserInputException(messageUtil.getMessage(MessageKeyEnum.PATIENT_LASTNAME_PATTERN_ERROR.getKey()));
        }
    }

    /**
     * Validates the format and presence of patient UUID.
     *
     * @param id the patient id as String.
     */
    public void validatePatientId(String id) throws InvalidUuidException {
        if (StringUtils.isBlank(id)) {
            throw new InvalidUuidException(messageUtil.getMessage(MessageKeyEnum.BLANK_UUID.getKey()));
        }
        if (!UUID_PATTERN.matcher(id).matches()) {
            throw new InvalidUuidException(messageUtil.getMessage(MessageKeyEnum.INVALID_UUID_FORMAT.getKey()));
        }
    }

    public List<String> validatePatientName(String name) {
        List<String> errors = new ArrayList<>();
        if (StringUtils.isBlank(name)) {
            errors.add(ApplicationConstants.QUERY_PARAMS_CANNOT_NULL);
        }
        return errors;
    }

    public List<String> validatePatients(PatientDto patientDto) {
        List<String> errors = new ArrayList<>();

        if (StringUtils.isBlank(patientDto.getFirstName())) {
            String message = messageUtil.getMessage(MessageKeyEnum.PATIENT_FIRSTNAME_ERROR.getKey());
            errors.add(message);
        } else if (!NAME_PATTERN.matcher(patientDto.getFirstName()).matches()) {
            String message = messageUtil.getMessage(MessageKeyEnum.PATIENT_FIRSTNAME_PATTERN_ERROR.getKey());
            errors.add(message);
        }
        if (StringUtils.isBlank(patientDto.getLastName())) {
            String message = messageUtil.getMessage(MessageKeyEnum.PATIENT_LASTNAME_ERROR.getKey());
            errors.add(message);
        } else if (!NAME_PATTERN.matcher(patientDto.getLastName()).matches()) {
            String message = messageUtil.getMessage(MessageKeyEnum.PATIENT_LASTNAME_ERROR.getKey());
            errors.add(message);
        }
        return errors;
    }
}
