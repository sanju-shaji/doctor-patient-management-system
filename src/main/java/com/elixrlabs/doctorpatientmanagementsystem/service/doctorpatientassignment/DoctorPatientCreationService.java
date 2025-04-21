package com.elixrlabs.doctorpatientmanagementsystem.service.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.DoctorPatientAssignmentDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctorpatientassignment.DoctorPatientAssignmentModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment.DoctorPatientAssignmentRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.PostResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctorpatientassignment.DoctorPatientUnAssignmentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Service Class for creating doctor-patient-assignment
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class DoctorPatientCreationService {
    private final DoctorPatientAssignmentRepository doctorPatientAssignmentRepository;
    private final MessageUtil messageUtil;
    private final DoctorPatientUnAssignmentValidator doctorPatientUnAssignmentValidator;
    /**
     * Method which contains the business logic to Post the assignments data to database
     *
     * @param assignmentDto-contains the data which is to be posted to the database
     * @return ResponseEntity in which the desired data is set for response
     */

    public ResponseEntity<PostResponse> createDoctorPatientAssignment(DoctorPatientAssignmentDto assignmentDto) {
        DoctorPatientAssignmentModel doctorPatientAssignmentModel = DoctorPatientAssignmentModel.builder()
                .id(UUID.randomUUID()).patientId(assignmentDto.getPatientId())
                .doctorId(assignmentDto.getDoctorId())
                .dateOfAdmission(Date.from(Instant.now()))
                .isUnAssigned(false)
                .build();
        DoctorPatientAssignmentModel savedAssignmentData = doctorPatientAssignmentRepository.save(doctorPatientAssignmentModel);
        PostResponse postAssignmentResponse = PostResponse.builder()
                .success(true)
                .id(savedAssignmentData.getId())
                .doctorId(savedAssignmentData.getDoctorId())
                .patientId(savedAssignmentData.getPatientId())
                .dateOfAdmission(savedAssignmentData.getDateOfAdmission())
                .build();
        log.info(postAssignmentResponse.toString());
        return new ResponseEntity<>(postAssignmentResponse, HttpStatus.OK);
    }
    /**
     * Method which contains the business logic to Post the UnAssign data to database
     *
     * @return ResponseEntity in which the desired data is set for response
     */

    public ResponseEntity<BaseResponse> unAssignDoctorFromPatient(DoctorPatientAssignmentDto dto) throws Exception{
        doctorPatientUnAssignmentValidator.validateDoctorPatientCombination(dto.getDoctorId(), dto.getPatientId());
        List<DoctorPatientAssignmentModel> doctorPatientAssignments = doctorPatientAssignmentRepository.findByDoctorIdAndPatientIdAndIsUnAssignedFalse(dto.getDoctorId(), dto.getPatientId());
        for (DoctorPatientAssignmentModel assignment : doctorPatientAssignments) {
            assignment.setIsUnAssigned(true);
        }
        doctorPatientAssignmentRepository.saveAll(doctorPatientAssignments);

        String message = messageUtil.getMessage(MessageKeyEnum.DOCTOR_SUCCESSFULLY_UNASSIGNED_FROM_PATIENT.getKey());
        BaseResponse baseResponse = BaseResponse.builder()
                .success(true)
                .messages(List.of(message))
                .build();
        return ResponseEntity.ok(baseResponse);
    }
}
