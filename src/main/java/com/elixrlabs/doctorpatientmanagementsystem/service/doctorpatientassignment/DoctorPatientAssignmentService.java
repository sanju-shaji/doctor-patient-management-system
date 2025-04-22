package com.elixrlabs.doctorpatientmanagementsystem.service.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.DoctorPatientAssignmentDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctorpatientassignment.DoctorPatientAssignmentModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment.DoctorPatientAssignmentRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.PostResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.ResponseBuilder;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctorpatientassignment.DoctorPatientAssignmentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**
 * Service Class for creating doctor-patient-assignment
 */
@RequiredArgsConstructor
@Service
public class DoctorPatientAssignmentService {
    private final DoctorPatientAssignmentRepository doctorPatientAssignmentRepository;
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
        DoctorPatientAssignmentModel savedAssignmentData = doctorPatientAssignmentRepository.save(doctorPatientAssignmentModel);
        return responseBuilder.buildSuccessAssignDoctorToPatient(savedAssignmentData);

    }
}
