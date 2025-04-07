package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientDeletionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<BaseResponse> ResponseDto(@PathVariable String patientId) {
        return patientDeletionResourceService.deletePatientById(patientId);
    }
}
