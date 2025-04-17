package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.PatientAlreadyAssignedException;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctorpatientassignment.DoctorPatientAssignmentModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment.DoctorPatientAssignmentRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.ResponseBuilder;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service to handle doctor deletion and ensure no patients are assigned before deletion.
 */
@Service
public class DoctorDeletionService {

    private final DoctorValidation doctorValidation;
    private final DoctorRepository doctorRepository;
    private final MessageUtil messageUtil;
    private final DoctorPatientAssignmentRepository doctorPatientAssignmentRepository;
    private final ResponseBuilder responseBuilder;

    public DoctorDeletionService(DoctorValidation doctorValidation, DoctorRepository doctorRepository, MessageUtil messageUtil, DoctorPatientAssignmentRepository doctorPatientAssignmentRepository, ResponseBuilder responseBuilder) {
        this.doctorValidation = doctorValidation;
        this.doctorRepository = doctorRepository;
        this.messageUtil = messageUtil;
        this.doctorPatientAssignmentRepository = doctorPatientAssignmentRepository;
        this.responseBuilder = responseBuilder;
    }

    /**
     * Deletes a doctor by their unique doctor ID.
     * This method performs the following actions:
     * - Validates if the provided doctor ID is in the correct format.
     * - Searches for the doctor with the given ID.
     * - If the doctor is found, it checks if any patients are assigned to the doctor.
     * - If there are assigned patients, it throws an exception to prevent deletion.
     * - If no patients are assigned, the doctor is deleted from the database.
     */
     public ResponseEntity<BaseResponse> deleteDoctorById(String doctorId) throws InvalidUuidException, DataNotFoundException {
        doctorValidation.validateDoctorId(doctorId);
        Optional<DoctorEntity> doctorEntityOptional = doctorRepository.findById(UUID.fromString(doctorId));
        if (doctorEntityOptional.isEmpty()) {
            String message = messageUtil.getMessage(MessageKeyEnum.NO_DOCTOR_FOUND.getKey(),doctorId);
            throw new DataNotFoundException(message);
        }
         List<DoctorPatientAssignmentModel> assignedPatients = doctorPatientAssignmentRepository.findByDoctorId(UUID.fromString(doctorId));
        if (!assignedPatients.isEmpty()) {
            String message = messageUtil.getMessage(MessageKeyEnum.PATIENT_ASSIGNED_TO_DOCTOR.getKey());
            throw new PatientAlreadyAssignedException(message);
        }
        doctorRepository.deleteById(UUID.fromString(doctorId));
        String message = messageUtil.getMessage(MessageKeyEnum.DELETE_DOCTOR_SUCCESSFULLY.getKey());
        return responseBuilder.buildSuccessDeleteResponse(List.of(message));
    }
}
