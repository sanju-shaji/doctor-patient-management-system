package com.elixrlabs.doctorpatientmanagementsystem.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorResponseDto;
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
     * @param doctorResponseDto-Which contains the data passed in request body
     * @return ResponseEntity of type DoctorResponseDto
     */
    @PostMapping(ApplicationConstants.POST_DOCTOR_API)
    public ResponseEntity<DoctorResponseDto> postDoctor(@RequestBody DoctorResponseDto doctorResponseDto) {
        return doctorCreationService.createDoctor(doctorResponseDto);

    }
}
