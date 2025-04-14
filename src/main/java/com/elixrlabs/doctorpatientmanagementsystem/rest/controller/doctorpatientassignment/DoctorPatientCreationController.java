package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.PostDoctorPatientAssignmentDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctorpatientassignment.DoctorPatientCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller class for the doctor module
 */
@RestController
@RequiredArgsConstructor
public class DoctorPatientCreationController {
    private final DoctorPatientCreationService doctorCreationService;
    @PostMapping(ApiConstants.ASSIGN_DOCTOR_PATIENT)
    public ResponseEntity<BaseResponse> createDoctorPatientAssignment(@RequestBody PostDoctorPatientAssignmentDto assignmentDto){
        return doctorCreationService.createDoctorPatientAssignment(assignmentDto);
    }
}
