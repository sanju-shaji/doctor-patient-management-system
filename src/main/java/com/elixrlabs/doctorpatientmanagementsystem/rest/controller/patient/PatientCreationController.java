package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DPMSConstants;
import com.elixrlabs.doctorpatientmanagementsystem.constants.DataBaseConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PostPatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.ResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling patient creation requests
 * provides an API endpoint to add new patients
 */

@RestController
@RequestMapping
public class PatientCreationController {
    @Autowired
    private PatientCreationService patientCreationService;

    @PostMapping(DPMSConstants.POST_PATIENTS_API)
    public ResponseEntity<ResponseDto> createPatient(@RequestBody PostPatientDto postPatientDto) {
        ResponseDto response = patientCreationService.createPatient(postPatientDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
