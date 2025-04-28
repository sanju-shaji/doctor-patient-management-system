package com.elixrlabs.doctorpatientmanagementsystem.service.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.DoctorPatientAssignmentDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.PatientAlreadyAssignedException;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctorpatientassignment.DoctorPatientAssignmentModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment.DoctorPatientAssignmentRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.PostResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.ResponseBuilder;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctorpatientassignment.DoctorPatientAssignmentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

/**
 * Service Class for creating doctor-patient-assignment
 */
@RequiredArgsConstructor
@Service
public class DoctorPatientAssignmentService {
    private final DoctorPatientAssignmentRepository doctorPatientAssignmentRepository;
    private final MessageUtil messageUtil;
    private final DoctorPatientAssignmentValidator doctorPatientAssignmentValidator;
    private final ResponseBuilder responseBuilder;

    /**
     * Method which contains the business logic to Post the assignments data to database
     *
     * @param assignmentDto-contains the data which is to be posted to the database
     * @return ResponseEntity in which the desired data is set for response
     */

    public ResponseEntity<PostResponse> createDoctorPatientAssignment(DoctorPatientAssignmentDto assignmentDto) {
        doctorPatientAssignmentValidator.validateAssignmentDto(assignmentDto);
        DoctorPatientAssignmentModel doctorPatientAssignmentModel = new DoctorPatientAssignmentModel(assignmentDto);
        Optional<DoctorPatientAssignmentModel> doctorPatientAssignments = doctorPatientAssignmentRepository.findByDoctorIdAndPatientIdAndIsUnAssigned(UUID.fromString(assignmentDto.getDoctorId()), UUID.fromString(assignmentDto.getPatientId()),false);
        if (doctorPatientAssignments.isPresent()) {
            throw new PatientAlreadyAssignedException(messageUtil.getMessage(MessageKeyEnum.DUPLICATE_DOCTOR_PATIENT_ASSIGNMENT.getKey()));
        }
        DoctorPatientAssignmentModel savedAssignmentData = doctorPatientAssignmentRepository.save(doctorPatientAssignmentModel);
        return responseBuilder.buildSuccessAssignDoctorToPatient(savedAssignmentData);
    }

    /**
     * Method which contains the business logic to Post the UnAssign data to database
     *
     * @return ResponseEntity in which the desired data is set for response
     */

    public ResponseEntity<BaseResponse> unAssignDoctorFromPatient(DoctorPatientAssignmentDto dto) {
        List<DoctorPatientAssignmentModel> doctorPatientAssignments = doctorPatientAssignmentRepository.findByDoctorIdAndPatientIdAndIsUnAssignedFalse(dto.getDoctorId(), dto.getPatientId());
        if (doctorPatientAssignments.isEmpty()) {
            String message = messageUtil.getMessage(MessageKeyEnum.DOCTOR_NOT_ASSIGNED_TO_PATIENT.getKey());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(BaseResponse.builder()
                            .success(false)
                            .errors(List.of(message))
                            .build());
        }
        for (DoctorPatientAssignmentModel assignment : doctorPatientAssignments) {
            assignment.setUnAssigned(true);
        }
        doctorPatientAssignmentRepository.saveAll(doctorPatientAssignments);

        String message = messageUtil.getMessage(MessageKeyEnum.DOCTOR_SUCCESSFULLY_UNASSIGNED_FROM_PATIENT.getKey());
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.builder()
                        .success(true)
                        .messages(List.of(message))
                        .build());
    }
}
