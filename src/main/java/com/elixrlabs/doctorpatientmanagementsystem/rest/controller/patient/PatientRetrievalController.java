package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.DoctorPatientAssignmentResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientRetrievalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for retrieving patient-related data.
 * Provides endpoints to :
 * Search patients by name
 * Fetch patient details by Id
 * Get list of patients assigned to a specific doctor
 */
@RestController
public class PatientRetrievalController {
    private final PatientRetrievalService patientRetrievalService;
    //Constructor to inject PatientRetrievalService

    public PatientRetrievalController(PatientRetrievalService patientRetrievalService) {
        this.patientRetrievalService = patientRetrievalService;
    }

    @GetMapping(ApiConstants.GET_PATIENT_BY_NAME_API)
    public ResponseEntity<PatientResponseDto> searchPatientsByNamePrefix(@RequestParam String name) {
        return patientRetrievalService.getPatientsByNamePrefixWithValidation(name);
    }

    /**
     * Endpoint to retrieve patient's details by Id
     */
    @GetMapping(ApiConstants.GET_PATIENT_BY_ID_API)
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable String id) throws Exception {
        return patientRetrievalService.getPatientById(id);
    }

    /**
     * Endpoint to get the list of patients assigned to a specific doctor
     */
    @GetMapping(ApiConstants.GET_PATIENTS_BY_DOCTOR_ID)
    public ResponseEntity<DoctorPatientAssignmentResponse> getPatientList(@RequestParam String doctorId) throws Exception {
        return patientRetrievalService.getPatientsWithDoctor(doctorId);
    }
}
