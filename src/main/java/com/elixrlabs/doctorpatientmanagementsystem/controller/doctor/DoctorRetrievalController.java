package com.elixrlabs.doctorpatientmanagementsystem.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiEndPointConstants;
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
     * Retrieves doctor details by name from the system.
     */
    @GetMapping(ApiEndPointConstants.GET_API_END_POINT)
    public ResponseEntity<DoctorListResponse> getDoctorByName(@RequestParam(value = ApiEndPointConstants.PARAM_DOCTOR_NAME, required = true) String name) {
        return doctorRetrievalService.retrieveDoctorByName(name);
    }
}
