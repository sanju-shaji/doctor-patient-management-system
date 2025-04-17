package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApiConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorDeletionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling doctor deletion requests.
 * Ensures the doctor can only be deleted if no patients are assigned.
 */
@RestController
public class DoctorDeletionController {

    private final DoctorDeletionService doctorDeletionService;

    public DoctorDeletionController(DoctorDeletionService doctorDeletionService) {
        this.doctorDeletionService = doctorDeletionService;
    }

     /**
     * Deletes a doctor by their ID after ensuring they have no patients assigned.
     * @param doctorId the ID of the doctor to be deleted
     * @return ResponseEntity containing success or error message
     * @throws InvalidUuidException if the doctor ID format is invalid
     * @throws DataNotFoundException if no doctor is found for the provided ID
     */

    @DeleteMapping(ApiConstants.PATCH_DOCTOR_BY_ID)
    public ResponseEntity<BaseResponse> deleteDoctorController(@PathVariable String doctorId) throws InvalidUuidException, DataNotFoundException {
        return doctorDeletionService.deleteDoctorById(doctorId);
    }
}
