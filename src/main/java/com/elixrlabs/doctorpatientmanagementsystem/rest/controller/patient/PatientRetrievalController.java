package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientRetrievalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller Class for GetByName/Patient Module
 */
@RestController
public class PatientRetrievalController {
    private final PatientRetrievalService patientRetrievalService;

    public PatientRetrievalController(PatientRetrievalService patientRetrievalService) {
        this.patientRetrievalService = patientRetrievalService;
    }

    @GetMapping(ApiConstants.GET_PATIENT_BY_NAME_API)
    public ResponseEntity<PatientResponseDto> searchPatientsByNamePrefix(@RequestParam String name) {
        return patientRetrievalService.getPatientsByNamePrefixWithValidation(name);
    }

    @GetMapping(ApiConstants.GET_PATIENT_BY_ID_API)
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable String id) throws Exception {
        return patientRetrievalService.getPatientById(id);
    }
}
