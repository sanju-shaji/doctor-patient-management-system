package com.elixrlabs.doctorpatientmanagementsystem.validation.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Validator class for validating doctor name input.
 */
@RequiredArgsConstructor
@Component
public class DoctorValidation {
    private final MongoTemplate mongoTemplate;
    private final PatientRepository patientRepository;
    private final MessageUtil messageUtil;

    /**
     * method to check if the string is empty and if string follows the specified pattern
     *
     * @param string The string which is to be validated
     * @param Pattern The pattern which is to be matched with the string for validation
     * @param emptyStringError error message if the string is empty
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
                ApplicationConstants.REGEX_ALPHABET_PATTERN, messageUtil.getMessage(MessageKeyEnum.EMPTY_FIRSTNAME, null),
                messageUtil.getMessage(MessageKeyEnum.FIRSTNAME_PATTERN_ERROR, null));
        validateString(doctor.getLastName(),
                ApplicationConstants.REGEX_ALPHABET_PATTERN, messageUtil.getMessage(MessageKeyEnum.EMPTY_LASTNAME, null),
                messageUtil.getMessage(MessageKeyEnum.LASTNAME_PATTERN_ERROR, null));
        validateString(doctor.getDepartment(),
                ApplicationConstants.REGEX_DEPARTMENTNAME_PATTERN, messageUtil.getMessage(MessageKeyEnum.EMPTY_DEPARTMENTNAME, null),
                messageUtil.getMessage(MessageKeyEnum.DEPARTMENTNAME_PATTERN_ERROR, null));
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

    public Boolean validateDoctorName(String name) {
        return StringUtils.isBlank(name);
    }

    /**
     * method to validate if patient exists and if patient is assigned to any doctors
     *
     * @param id-patientId
     * @return true or false
     */
    public boolean isPatientAssignedToDoctor(String id) throws DataNotFoundException {
        if (!patientRepository.existsById(UUID.fromString(id))) {
            throw new DataNotFoundException(messageUtil.getMessage(MessageKeyEnum.PATIENT_NOT_FOUND_ERROR, null), UUID.fromString(id));
        }
        UUID patientID = UUID.fromString(id);
        Query query = new Query();
        query.addCriteria(Criteria.where(ApplicationConstants.PATIENT_ID).is(patientID));
        return mongoTemplate.exists(query, ApplicationConstants.ASSIGNMENT_COLLECTION);
    }
}
