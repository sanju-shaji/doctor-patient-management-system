package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorPatchResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorModificationService;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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
     * @param doctorId the id of the doctor to update
     * @param patch    the details need to be changed
     * @return ResponseEntity with the result ater updating the doctor
     */
    @PatchMapping(ApiConstants.PATCH_END_POINT)
    public ResponseEntity<DoctorPatchResponse> patchDoctor(@PathVariable String doctorId, @RequestBody JsonPatch patch) throws Exception {
        return doctorModificationService.applyPatchToDoctor(doctorId, patch);
    }
}
