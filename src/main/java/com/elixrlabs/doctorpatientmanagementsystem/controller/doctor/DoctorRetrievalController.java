package com.elixrlabs.doctorpatientmanagementsystem.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorRetrievalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/** Controller for retrieving doctor details based on user input. */
@RestController
@RequestMapping("/doctor")
@Slf4j
public class DoctorRetrievalController {

    private final DoctorRetrievalService doctorRetrievalService;
    public DoctorRetrievalController(DoctorRetrievalService doctorRetrievalService) {
        this.doctorRetrievalService = doctorRetrievalService;
    }
    /** Retrieves doctor details by name from the system. */
    @GetMapping("/get")
    public BaseResponse getDoctorByName(@RequestParam("name") String name) {
        return doctorRetrievalService.retriveDoctorByName(name);
    }
}
