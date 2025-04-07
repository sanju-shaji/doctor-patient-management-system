package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.DoctorPatientAssignmentRepository;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
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

    public PatientDeletionService(PatientRepository patientRepository, DoctorPatientAssignmentRepository doctorPatientAssignmentRepository, PatientValidation patientValidation) {
        this.patientRepository = patientRepository;
        this.doctorPatientAssignmentRepository = doctorPatientAssignmentRepository;
        this.patientValidation = patientValidation;
    }

    public ResponseEntity<BaseResponse> deletePatientById(String patientId) {
        try {
            List<String> validateErrors = patientValidation.validatePatientId(patientId);
            if (!validateErrors.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.builder()
                        .success(false)
                        .errors(validateErrors)
                        .build());
            }
            Optional<PatientModel> patientModelOptional = patientRepository.findById(UUID.fromString(patientId));
            if (!patientModelOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.builder()
                        .success(false)
                        .errors(List.of(ApplicationConstants.NO_PATIENT_FOUND + patientId))
                        .build());
            }
            boolean isAssigned = !doctorPatientAssignmentRepository.findByPatientId(UUID.fromString(patientId)).isEmpty();
            if (isAssigned) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.builder()
                        .success(false)
                        .errors(List.of(ApplicationConstants.PATIENT_ASSIGNED_TO_DOCTOR))
                        .build());

            }
            patientRepository.deleteById(UUID.fromString(patientId));
            return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.builder()
                    .success(false)
                    .errors(List.of(ApplicationConstants.PATIENT_DELETED_SUCCESSFULLY))
                    .build());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.builder()
                    .success(false)
                    .errors(List.of(ApplicationConstants.SERVER_ERROR))
                    .build());
        }
    }
}
