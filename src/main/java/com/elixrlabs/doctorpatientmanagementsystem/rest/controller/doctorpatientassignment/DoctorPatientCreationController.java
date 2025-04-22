package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.DoctorPatientAssignmentDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.PostResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctorpatientassignment.DoctorPatientCreationService;
import lombok.RequiredArgsConstructor;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller class for the creating doctor-patient-assignments
 */
@RestController
@RequiredArgsConstructor
public class DoctorPatientCreationController {
    private final DoctorPatientCreationService doctorCreationService;


    /**
     * Api mapping for assignDoctorToPatient endpoint
     *
     * @param assignmentDto - Which contains the data passed in request body
     * @return - response entity of type PostResponse
     */
    @PostMapping(ApiConstants.ASSIGN_DOCTOR_PATIENT)
    public ResponseEntity<PostResponse> createDoctorPatientAssignment(@RequestBody DoctorPatientAssignmentDto assignmentDto) {
        return doctorCreationService.createDoctorPatientAssignment(assignmentDto);
    }

    /**
     * Api mapping for UnAssignDoctorFromPatient endpoint
     *
     * @param assignmentDto - Which contains the data passed in request body
     * @return - response entity of type PostResponse
     */
    @PostMapping(ApiConstants.UN_ASSIGN_DOCTOR_FROM_PATIENT)
    public ResponseEntity<BaseResponse> unAssignDoctor(@RequestBody DoctorPatientAssignmentDto assignmentDto) {
        return doctorCreationService.unAssignDoctorFromPatient(assignmentDto);
    }
    /**
     * Api mapping for UnAssignDoctorFromPatient endpoint
     *
     * @param assignmentDto - Which contains the data passed in request body
     * @return - response entity of type PostResponse
     */
    @PostMapping(ApiConstants.UN_ASSIGN_DOCTOR_FROM_PATIENT)
    public ResponseEntity<BaseResponse> unAssignDoctor(@RequestBody DoctorPatientAssignmentDto assignmentDto)throws Exception {
        return doctorCreationService.unAssignDoctorFromPatient(assignmentDto);
    }
}
