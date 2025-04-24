package com.elixrlabs.doctorpatientmanagementsystem.util;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctorpatientassignment.DoctorPatientAssignmentModel;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorPatchResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.PostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Utility class for building standardized HTTP responses.
 */
@Component
public class ResponseBuilder {

    /**
     * Builds a successful patch response with updated Doctor data.
     *
     * @param data the updated DoctorDto
     * @return ResponseEntity containing the success response and updated data
     */
    public ResponseEntity<DoctorPatchResponse> buildSuccessPatchResponse(DoctorDto data) {
        DoctorPatchResponse doctorPatchResponse = DoctorPatchResponse.builder()
                .success(true)
                .doctorDto(data)
                .build();
        return new ResponseEntity<>(doctorPatchResponse, HttpStatus.OK);
    }

    /**
     * This method builds success response and related success message.
     *
     * @param messages success response message
     * @return ResponseEntity containing the success response and related success message
     */
    public ResponseEntity<BaseResponse> buildSuccessDeleteResponse(List<String> messages) {
        BaseResponse baseResponse = BaseResponse.builder()
                .success(true)
                .messages(messages)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    /**
     * Builds a successful response after assigning a doctor to a patient.
     */
    public ResponseEntity<PostResponse> buildSuccessAssignDoctorToPatient(DoctorPatientAssignmentModel savedAssignmentModel){
        PostResponse postAssignmentResponse =PostResponse.builder()
                .success(true)
                .id(savedAssignmentModel.getId())
                .doctorId(savedAssignmentModel.getDoctorId())
                .patientId(savedAssignmentModel.getPatientId())
                .dateOfAdmission(savedAssignmentModel.getDateOfAdmission())
                .build();
        return new ResponseEntity<>(postAssignmentResponse,HttpStatus.OK);
    }
}
