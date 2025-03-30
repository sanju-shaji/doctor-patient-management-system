package com.elixrlabs.doctorpatientmanagementsystem.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DPMSConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorRetrievalService;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Controller class for retrieving data from database
 */
@RestController
public class DoctorRetrievalController {
    @Autowired
    DoctorRetrievalService service;

    /**
     * Method to retrieve doctor data from database using ID
     */
    @GetMapping(DPMSConstants.GET_DOCTOR_BY_ID_API)
    public ResponseEntity<DoctorResponseDto> getDoctorByID(@PathVariable String id) {
        DoctorValidation doctorValidation = new DoctorValidation();
        if (!doctorValidation.isValidUUID(id)) {
            DoctorResponseDto responseDto = new DoctorResponseDto();
            responseDto.setSuccess(false);
            responseDto.setError(List.of(DPMSConstants.INVALID_UUID_ERROR));
            return ResponseEntity.status(400).body(responseDto);
        }
        UUID uuid = UUID.fromString(id);
        return service.getDoctorsById(uuid);
    }
}
