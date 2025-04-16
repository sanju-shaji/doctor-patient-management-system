package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatchPatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientModificationService;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PatientModificationController {
    private final PatientModificationService patientModificationService;

    public PatientModificationController(PatientModificationService patchPatientService) {
        this.patientModificationService = patchPatientService;
    }

    @PatchMapping(ApiConstants.PATIENT_BY_ID)
    public ResponseEntity<PatchPatientResponse> patchPatientById(@PathVariable String id, @RequestBody JsonPatch patch) throws Exception {
        return patientModificationService.applyPatch(id, patch);
    }
}
