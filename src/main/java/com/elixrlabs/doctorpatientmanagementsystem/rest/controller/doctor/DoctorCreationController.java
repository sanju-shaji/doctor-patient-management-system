package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorCreationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * controller class for the doctor module
 */
@RestController
public class DoctorCreationController {
    private final DoctorCreationService doctorCreationService;

    public DoctorCreationController(DoctorCreationService doctorCreationService) {
        this.doctorCreationService = doctorCreationService;
    }

    /**
     * Api mapping for posting doctor information
     *
     * @param doctorResponse-Which contains the data passed in request body
     * @return ResponseEntity of type DoctorResponseDto
     */
    @PostMapping(ApiConstants.DOCTORS_END_POINT)
    public ResponseEntity<DoctorResponse> createDoctor(@RequestBody DoctorDto doctorResponse) throws Exception {
        return doctorCreationService.createDoctor(doctorResponse);
    }
}
