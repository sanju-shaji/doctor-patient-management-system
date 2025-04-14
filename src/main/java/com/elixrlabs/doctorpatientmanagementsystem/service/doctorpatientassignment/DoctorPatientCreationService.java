package com.elixrlabs.doctorpatientmanagementsystem.service.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.PostDoctorPatientAssignmentDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctorpatientassignment.DoctorPatientAssignmentModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment.DoctorPatientAssignmentRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * Service Class for Doctor Module
 */
@RequiredArgsConstructor
@Service
public class DoctorPatientCreationService {
    private final DoctorPatientAssignmentRepository doctorPatientAssignmentRepository;

    /**
     * Method which contains the business logic to Post the assignments data to database
     *
     * @param assignmentDto-contains the data which is to be posted to the database
     * @return ResponseEntity in which the desired data is set for response
     */
    public ResponseEntity<BaseResponse> createDoctorPatientAssignment(PostDoctorPatientAssignmentDto assignmentDto) {
        if (assignmentDto.getDateOfAdmission() == null) {
            assignmentDto.setDateOfAdmission(new Date());
        }
        DoctorPatientAssignmentModel doctorPatientAssignmentModel = DoctorPatientAssignmentModel.builder()
                .id(UUID.randomUUID()).patientId(assignmentDto.getPatientId())
                .doctorId(assignmentDto.getPatientId())
                .dateOfAdmission(assignmentDto.getDateOfAdmission())
                .build();
        doctorPatientAssignmentRepository.save(doctorPatientAssignmentModel);
        BaseResponse postAssignmentResponse = new BaseResponse();
        postAssignmentResponse.setSuccess(true);
        return ResponseEntity.ok().body(postAssignmentResponse);
    }
}
