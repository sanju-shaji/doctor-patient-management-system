package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientDeletionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for handling requests related to deleting patient details who are not assigned to doctors.
 */

@RestController
@RequestMapping(ApiConstants.PATIENT_API)
public class PatientDeletionController {
    private final PatientDeletionService patientDeletionResourceService;

    public PatientDeletionController(PatientDeletionService patientDeletionResourceService) {
        this.patientDeletionResourceService = patientDeletionResourceService;
    }

    @DeleteMapping(ApiConstants.PATIENT_ID)
    public ResponseEntity<BaseResponse> deletePatientController(@PathVariable String patientId) throws Exception {
        return patientDeletionResourceService.deletePatientById(patientId);
    }
}
