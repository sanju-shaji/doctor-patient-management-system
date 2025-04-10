package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DPADto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.EmptyUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidExcetion;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DPAResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Service class for retrieving doctor details based on name search.
 */
@Service
@RequiredArgsConstructor
public class DoctorRetrievalService {
    private final DoctorValidation doctorValidation;
    private final DoctorRepository doctorRepository;
    private final MongoTemplate mongoTemplate;
    private final MessageUtil messageUtil;

    /**
     * Retrieves doctors by first doctorName, last doctorName, or both, with validation.
     */
    public ResponseEntity<DoctorListResponse> retrieveDoctorByName(String doctorName) {
        DoctorListResponse doctorListResponse = new DoctorListResponse();
        boolean doctorValidatorResponse = doctorValidation.validateDoctorName(doctorName);
        if (doctorValidatorResponse) {
            doctorListResponse.setSuccess(false);
            doctorListResponse.setErrors(Collections.singletonList((ApplicationConstants.EMPTY_NAME_QUERY_PARAM)));
            return new ResponseEntity<>(doctorListResponse, HttpStatus.BAD_REQUEST);
        }
        List<DoctorEntity> doctorEntityList = doctorRepository.findByName(doctorName);
        if (!doctorEntityList.isEmpty()) {
            List<DoctorDto> doctorsData = new ArrayList<>();
            for (DoctorEntity doctorEntity : doctorEntityList) {
                DoctorDto doctorDto = DoctorDto.builder()
                        .id(doctorEntity.getId())
                        .firstName(doctorEntity.getFirstName())
                        .lastName(doctorEntity.getLastName())
                        .department(doctorEntity.getDepartment()).build();
                doctorsData.add(doctorDto);
            }
            doctorListResponse.setSuccess(true);
            doctorListResponse.setDoctors(doctorsData);
            return new ResponseEntity<>(doctorListResponse, HttpStatus.OK);
        } else {
            doctorListResponse.setSuccess(false);
            doctorListResponse.setErrors(Collections.singletonList(ApplicationConstants.DOCTORS_NOT_FOUND + ApplicationConstants.COLON + ApplicationConstants.SINGLE_QUOTE + doctorName + ApplicationConstants.SINGLE_QUOTE));
            return new ResponseEntity<>(doctorListResponse, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method to retrieve single doctor data using id
     *
     * @param id-uuid
     * @return Doctor response object with a single doctor data
     */
    public ResponseEntity<DoctorResponse> getDoctorsById(String id) throws Exception {
        if (StringUtils.isBlank(id)) {
            throw new EmptyUuidException(messageUtil.getMessage(MessageKeyEnum.EMPTY_UUID, null));
        }
        if (!doctorValidation.isValidUUID(id)) {
            throw new InvalidUuidExcetion(messageUtil.getMessage(MessageKeyEnum.INVALID_UUID_ERROR, null));
        }
        UUID uuid = UUID.fromString(id);
        Optional<DoctorEntity> doctorEntity = doctorRepository.findById(uuid);
        if (doctorEntity.isPresent()) {
            DoctorResponse responseDto = DoctorResponse.builder().id(doctorEntity.get().getId())
                    .firstName(doctorEntity.get().getFirstName())
                    .lastName(doctorEntity.get().getLastName())
                    .department(doctorEntity.get().getDepartment())
                    .success(true).build();
            return ResponseEntity.ok().body(responseDto);
        }
        throw new DataNotFoundException(messageUtil.getMessage(MessageKeyEnum.USER_NOT_FOUND_ERROR, null), uuid);
    }

    /**
     * method to fetch list of doctors assigned to a patient using patient id
     *
     * @param patientId-id of patient
     * @return - DPAResponse with patient details and list of assigned doctors
     */
    public ResponseEntity<DPAResponse> getDoctorsWithPatient(String patientId) throws Exception {
        if (StringUtils.isEmpty(patientId)) {
            throw new EmptyUuidException(messageUtil.getMessage(MessageKeyEnum.EMPTY_UUID, null));
        }
        if (!doctorValidation.isValidUUID(patientId)) {
            throw new InvalidUuidExcetion(messageUtil.getMessage(MessageKeyEnum.INVALID_UUID_ERROR, null));
        }
        if (!doctorValidation.isPatientAssignedToDoctor(patientId)) {
            throw new DataNotFoundException(messageUtil.getMessage(MessageKeyEnum.PATIENT_NOT_ASSIGNED, null), UUID.fromString(patientId));
        }
        UUID patientUUID = UUID.fromString(patientId);
        Aggregation aggregation = newAggregation(
                match(org.springframework.data.mongodb.core.query.Criteria.where(ApplicationConstants.ID).is(patientUUID)),
                lookup(ApplicationConstants.ASSIGNMENT_COLLECTION, ApplicationConstants.ID, ApplicationConstants.PATIENT_ID, ApplicationConstants.ASSIGNMENTS),
                unwind(ApplicationConstants.ASSIGNMENTS, true),
                lookup(ApplicationConstants.DOCTORS_COLLECTION, ApplicationConstants.ASSIGNMENTS_DOCTOR_ID, ApplicationConstants.ID, ApplicationConstants.ASSIGNMENTS_DOCTOR),
                unwind(ApplicationConstants.ASSIGNMENTS_DOCTOR, true),
                group(ApplicationConstants.ID).first(ApplicationConstants.FIRST_NAME).as(ApplicationConstants.FIRST_NAME)
                        .first(ApplicationConstants.LAST_NAME).as(ApplicationConstants.LAST_NAME)
                        .push(ApplicationConstants.ASSIGNMENTS_DOCTOR).as(ApplicationConstants.DOCTORS)
        );
        AggregationResults<DPADto> results = mongoTemplate.aggregate(aggregation, ApplicationConstants.PATIENT_COLLECTION, DPADto.class);
        DPADto resultData = results.getUniqueMappedResult();
        DPAResponse dpaResponse = DPAResponse.builder()
                .id(resultData.getId())
                .firstName(resultData.getFirstName())
                .lastName(resultData.getLastName())
                .doctors(resultData.getDoctors())
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(dpaResponse);
    }
}
