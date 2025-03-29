package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientGetByNameService;
import com.elixrlabs.doctorpatientmanagementsystem.validations.patient.PatientGetByNameValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
/**
 * Controller Class for GetByName/Patient Module
 */
@RestController
@RequestMapping("/api/patients")
public class PatientGetByNameController {
    private PatientGetByNameService patientGetByNameService;

    @Autowired
    public PatientGetByNameController(PatientGetByNameService patientGetByNameService) {
        this.patientGetByNameService = patientGetByNameService;
    }

    @GetMapping("/search")
    public ResponseEntity<PatientResponseDto> searchPatientsByNamePrefix(@RequestParam String name) {
        List<PatientDto> patients = patientGetByNameService.getPatientsByNamePrefix(name);
        List<String> errors = PatientGetByNameValidations.validatePatientName(name);
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PatientResponseDto(errors, false));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new PatientResponseDto(true, patients));
    }
}
