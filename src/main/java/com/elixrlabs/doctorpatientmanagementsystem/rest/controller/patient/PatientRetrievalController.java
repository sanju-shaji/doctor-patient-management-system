package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientRetrievalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Class for GetByName/Patient Module
 */
@RestController
public class PatientRetrievalController {
    private final PatientRetrievalService patientGetByNameService;

    public PatientRetrievalController(PatientRetrievalService patientGetByNameService) {
        this.patientGetByNameService = patientGetByNameService;
    }

    @GetMapping(ApiConstants.PATIENT_API)
    public ResponseEntity<PatientResponseDto> searchPatientsByNamePrefix(@RequestParam String name) throws Exception {
        return patientGetByNameService.getPatientsByNamePrefixWithValidation(name);
    }
}
