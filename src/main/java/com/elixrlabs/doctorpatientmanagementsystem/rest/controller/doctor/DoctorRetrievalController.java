package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorRetrievalService;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Controller class for retrieving data from database
 */
@RestController
public class DoctorRetrievalController {
    @Autowired
    DoctorRetrievalService service;

    /**
     * Method to retrieve doctor data from database using ID
     */
    @GetMapping(ApplicationConstants.GET_DOCTOR_BY_ID_API)
    public ResponseEntity<DoctorDto> getDoctorByID(@PathVariable String id) {
        return service.getDoctorsById(id);
    }
}
