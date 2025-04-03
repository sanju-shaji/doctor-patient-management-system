package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorRetrievalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling requests related to retrieving doctor details.
 * It exposes an API endpoint to fetch doctor information based on the provided name.
 */
@RestController
public class DoctorRetrievalController {

    private final DoctorRetrievalService doctorRetrievalService;

    public DoctorRetrievalController(DoctorRetrievalService doctorRetrievalService) {
        this.doctorRetrievalService = doctorRetrievalService;
    }

    /**
     * Retrieves doctor details by doctorName from the system.
     */
    @GetMapping(ApplicationConstants.DOCTORS_END_POINT)
    public ResponseEntity<DoctorListResponse> getDoctorByName(@RequestParam(value = ApplicationConstants.PARAM_DOCTOR_NAME) String doctorName) {
        return doctorRetrievalService.retrieveDoctorByName(doctorName);
    }
}
