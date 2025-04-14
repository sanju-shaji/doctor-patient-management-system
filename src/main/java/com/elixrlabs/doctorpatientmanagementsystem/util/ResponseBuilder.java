package com.elixrlabs.doctorpatientmanagementsystem.util;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorPatchResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Utility class for building standardized HTTP responses.
 */
@Component
public class ResponseBuilder {

    /**
     * Builds a successful patch response with updated Doctor data.
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
}
