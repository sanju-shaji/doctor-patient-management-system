package com.elixrlabs.doctorpatientmanagementsystem.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DPMSConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.PostDoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorCreationService;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param postDoctorDto
     * @return
     */
    @PostMapping(DPMSConstants.POST_DOCTOR_API)
    public PostDoctorDto postDoctor(@RequestBody PostDoctorDto postDoctorDto) {
        return doctorCreationService.createDoctor(postDoctorDto);
    }
}
