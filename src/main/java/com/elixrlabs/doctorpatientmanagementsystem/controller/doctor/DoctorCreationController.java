package com.elixrlabs.doctorpatientmanagementsystem.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DPMSConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * controller class for the doctor module
 */
@RestController
public class DoctorCreationController {
    @Autowired
    DoctorCreationService doctorCreationService;

    /**
     * Api mapping for posting doctor information
     *
     * @param doctorResponseDto
     * @return
     */
    @PostMapping(DPMSConstants.POST_DOCTOR_API)
    public ResponseEntity<DoctorResponseDto> postDoctor(@RequestBody DoctorResponseDto doctorResponseDto) {
        return doctorCreationService.createDoctor(doctorResponseDto);
    }
}
