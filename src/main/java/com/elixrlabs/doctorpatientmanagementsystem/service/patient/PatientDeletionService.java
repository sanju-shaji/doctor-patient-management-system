package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.EmptyUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidExcetion;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.PatientAlreadyAssignedException;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.DoctorPatientAssignmentRepository;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for deleting patient details who are not assigned to doctors.
 */
@Service
public class PatientDeletionService {
    private final PatientRepository patientRepository;
    private final DoctorPatientAssignmentRepository doctorPatientAssignmentRepository;
    private final PatientValidation patientValidation;
    private final MessageUtil messageUtil;

    public PatientDeletionService(PatientRepository patientRepository,
                                  DoctorPatientAssignmentRepository doctorPatientAssignmentRepository,
                                  PatientValidation patientValidation,
                                  MessageUtil messageUtil) {
        this.patientRepository = patientRepository;
        this.doctorPatientAssignmentRepository = doctorPatientAssignmentRepository;
        this.patientValidation = patientValidation;
        this.messageUtil = messageUtil;
    }

    public ResponseEntity<BaseResponse> deletePatientById(String patientId) throws EmptyUuidException, DataNotFoundException, InvalidUuidExcetion, PatientAlreadyAssignedException {
        patientValidation.validatePatientId(patientId);
        Optional<PatientModel> patientModelOptional = patientRepository.findById(UUID.fromString(patientId));
        if (!patientModelOptional.isPresent()) {
            String message = messageUtil.getMessage(MessageKeyEnum.NO_PATIENT_FOUND.getKey());
            throw new DataNotFoundException(message, UUID.fromString(patientId));
        }
        boolean isAssigned = !doctorPatientAssignmentRepository.findByPatientId(UUID.fromString(patientId)).isEmpty();
        if (isAssigned) {
            String message = messageUtil.getMessage(MessageKeyEnum.PATIENT_ASSIGNED_TO_DOCTOR.getKey());
            throw new PatientAlreadyAssignedException(message);
        }
        patientRepository.deleteById(UUID.fromString(patientId));
        String message = messageUtil.getMessage(MessageKeyEnum.PATIENT_DELETED_SUCCESSFULLY.getKey());
        return buildSuccess(HttpStatus.OK, List.of(message));
    }

    private ResponseEntity<BaseResponse> buildError(HttpStatus status, List<String> errors) {
        BaseResponse baseResponse = BaseResponse.builder()
                .success(false)
                .errors(errors)
                .build();
        return new ResponseEntity<>(baseResponse, status);
    }

    private ResponseEntity<BaseResponse> buildSuccess(HttpStatus status, List<String> messages) {
        BaseResponse baseResponse = BaseResponse.builder()
                .success(true)
                .messages(messages)
                .build();
        return new ResponseEntity<>(baseResponse, status);
    }
}
