package com.elixrlabs.doctorpatientmanagementsystem.service.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.DoctorPatientAssignmentDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctorpatientassignment.DoctorPatientAssignmentModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment.DoctorPatientAssignmentRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.PostResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.ResponseBuilder;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctorpatientassignment.DoctorPatientAssignmentValidator;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctorpatientassignment.DoctorPatientUnAssignmentValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service Class for creating doctor-patient-assignment
 */
@Service
public class DoctorPatientAssignmentService {
    private final DoctorPatientAssignmentRepository doctorPatientAssignmentRepository;
    private final MessageUtil messageUtil;
    private final DoctorPatientUnAssignmentValidator doctorPatientUnAssignmentValidator;
    private final DoctorPatientAssignmentValidator doctorPatientAssignmentValidator;
    private final ResponseBuilder responseBuilder;

    public DoctorPatientAssignmentService(DoctorPatientAssignmentRepository doctorPatientAssignmentRepository, MessageUtil messageUtil, DoctorPatientUnAssignmentValidator doctorPatientUnAssignmentValidator, DoctorPatientAssignmentValidator doctorPatientAssignmentValidator, ResponseBuilder responseBuilder) {
        this.doctorPatientAssignmentRepository = doctorPatientAssignmentRepository;
        this.messageUtil = messageUtil;
        this.doctorPatientUnAssignmentValidator = doctorPatientUnAssignmentValidator;
        this.doctorPatientAssignmentValidator = doctorPatientAssignmentValidator;
        this.responseBuilder = responseBuilder;
    }

    /**
     * Method which contains the business logic to Post the assignments data to database
     *
     * @param assignmentDto-contains the data which is to be posted to the database
     * @return ResponseEntity in which the desired data is set for response
     */

    public ResponseEntity<PostResponse> createDoctorPatientAssignment(DoctorPatientAssignmentDto assignmentDto) {
        doctorPatientAssignmentValidator.validateAssignmentDto(assignmentDto);
        DoctorPatientAssignmentModel doctorPatientAssignmentModel = new DoctorPatientAssignmentModel(assignmentDto);
        DoctorPatientAssignmentModel savedAssignmentData = doctorPatientAssignmentRepository.save(doctorPatientAssignmentModel);
        return responseBuilder.buildSuccessAssignDoctorToPatient(savedAssignmentData);
    }

    /**
     * Method which contains the business logic to Post the UnAssign data to database
     *
     * @return ResponseEntity in which the desired data is set for response
     */

    public ResponseEntity<BaseResponse> unAssignDoctorFromPatient(DoctorPatientAssignmentDto doctorPatientAssignment) throws Exception {
        UUID doctorId = UUID.fromString(doctorPatientAssignment.getDoctorId());
        UUID patientId = UUID.fromString(doctorPatientAssignment.getPatientId());
        doctorPatientUnAssignmentValidator.validateDoctorPatientCombination(doctorId, patientId);
        DoctorPatientAssignmentModel doctorPatientAssignments = doctorPatientAssignmentRepository.findByDoctorIdAndPatientIdAndIsUnAssignedFalse(doctorId, patientId);
        doctorPatientAssignments.setUnAssigned(true);
        doctorPatientAssignmentRepository.save(doctorPatientAssignments);
        String message = messageUtil.getMessage(MessageKeyEnum.DOCTOR_SUCCESSFULLY_UNASSIGNED_FROM_PATIENT.getKey());
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.builder()
                        .success(true)
                        .messages(List.of(message))
                        .build());
    }
}
