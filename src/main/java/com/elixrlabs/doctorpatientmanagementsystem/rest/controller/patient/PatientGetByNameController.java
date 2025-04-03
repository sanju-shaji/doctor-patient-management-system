package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientGetByNameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller Class for GetByName/Patient Module
 */
@RestController
@RequestMapping("/api/patients")
public class PatientGetByNameController {
    private final PatientGetByNameService patientGetByNameService;

    public PatientGetByNameController(PatientGetByNameService patientGetByNameService) {
        this.patientGetByNameService = patientGetByNameService;
    }

    @GetMapping("/search")
    public ResponseEntity<PatientResponseDto> searchPatientsByNamePrefix(@RequestParam String name) {
        return patientGetByNameService.getPatientsByNamePrefixWithValidation(name);
    }
}
