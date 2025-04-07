package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorPatchResponse;

import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorModificationService;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * This Controller for handling HTTP PATCH requests related to doctor modifications.
 */
@RestController
public class DoctorModificationController {
    private final DoctorModificationService doctorModificationService;

    public DoctorModificationController(DoctorModificationService doctorModificationService) {
        this.doctorModificationService = doctorModificationService;
    }

    /**
     * This method receives a doctor ID and a JSON Patch request body, and
     * delegates the patching operation to the DoctorModificationService.
     *
     * @param id
     * @param patch
     * @return
     */
    @PatchMapping("/doctor/{id}")
    public ResponseEntity<DoctorPatchResponse> patchDoctor(@PathVariable String id, @RequestBody JsonPatch patch) {
        return doctorModificationService.applyPatchToDoctor(id, patch);
    }
}
