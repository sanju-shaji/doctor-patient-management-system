package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorRetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling requests related to retrieving doctor details.
 * It exposes an API endpoint to fetch doctor information based on the provided name.
 */
@RestController
@RequiredArgsConstructor
public class DoctorRetrievalController {

    private final DoctorRetrievalService doctorRetrievalService;

    /**
     * Retrieves doctor details by doctorName from the system.
     */
    @GetMapping(ApiConstants.DOCTORS_END_POINT)
    public ResponseEntity<DoctorListResponse> getDoctorByName(@RequestParam(value = ApplicationConstants.PARAM_DOCTOR_NAME) String doctorName) {
        return doctorRetrievalService.retrieveDoctorByName(doctorName);
    }

    /**
     * Api mapping for retrieving doctor information using ID
     *
     * @param id-Which contains the UUID of specific doctor data to be fetched
     * @return ResponseEntity of type DoctorResponseDto
     */
    @GetMapping(ApiConstants.GET_DOCTOR_BY_ID)
    public ResponseEntity<DoctorResponse> getDoctorByID(@PathVariable String id) {
        return doctorRetrievalService.getDoctorsById(id);
    }
}
