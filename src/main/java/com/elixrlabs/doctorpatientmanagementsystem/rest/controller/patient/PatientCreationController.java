package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientCreationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling patient creation requests
 * provides an API endpoint to add new patients
 */

@RestController
public class PatientCreationController {
    private final PatientCreationService patientCreationService;

    PatientCreationController(PatientCreationService patientCreationService) {
        this.patientCreationService = patientCreationService;
    }

    @PostMapping(ApiConstants.PATIENTS_API)
    public ResponseEntity<PatientResponse> createPatient(@RequestBody PatientDto patientDto) {
        return patientCreationService.createPatient(patientDto);
    }
}
