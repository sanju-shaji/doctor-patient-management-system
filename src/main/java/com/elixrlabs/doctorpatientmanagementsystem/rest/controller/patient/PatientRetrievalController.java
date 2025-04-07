package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.ResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientRetrievalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller Class for GetByName/Patient Module
 */
@RestController
@RequestMapping(ApiConstants.PATIENTS_API)
public class PatientRetrievalController {
    private final PatientRetrievalService patientRetrievalService;

    public PatientRetrievalController(PatientRetrievalService patientRetrievalService) {
        this.patientRetrievalService = patientRetrievalService;
    }

    @GetMapping("/search")
    public ResponseEntity<PatientResponseDto> searchPatientsByNamePrefix(@RequestParam String name) {
        return patientRetrievalService.getPatientsByNamePrefixWithValidation(name);
    }

    @GetMapping(ApiConstants.GET_PATIENT_BY_ID_API)
    public ResponseEntity<ResponseDto> getPatientById(@PathVariable String id) {
        return patientRetrievalService.getPatientById(id);
    }
}
