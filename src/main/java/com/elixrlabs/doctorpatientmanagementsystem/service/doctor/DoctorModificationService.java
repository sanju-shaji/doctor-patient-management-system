package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorPatchResponse;

import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * This class responsible for handling modifications to Doctor entities,
 * specifically applying JSON Patch operations to partially update doctor data.
 */
@Service
public class DoctorModificationService {
    private final DoctorRepository doctorRepository;
    private final ObjectMapper objectMapper;
    private final DoctorValidation doctorValidation;

    public DoctorModificationService(DoctorRepository doctorRepository, ObjectMapper objectMapper, DoctorValidation doctorValidation) {
        this.doctorRepository = doctorRepository;
        this.objectMapper = objectMapper;
        this.doctorValidation = doctorValidation;
    }

    /**
     * Applies a JSON Patch to a Doctor entity identified by the provided UUID.
     *
     * @param id
     * @param patch
     * @return
     */
    public ResponseEntity<DoctorPatchResponse> applyPatchToDoctor(String id, JsonPatch patch) {

        DoctorPatchResponse DoctorPatchResponse = new DoctorPatchResponse();
        if ((doctorValidation.isDoctorIdMissing(id))) {
            DoctorPatchResponse.setSuccess(false);
            DoctorPatchResponse.setErrors(Collections.singletonList(ApplicationConstants.MISSING_ID));
            return new ResponseEntity<>(DoctorPatchResponse, HttpStatus.BAD_REQUEST);
        }
        if (!(doctorValidation.validateUuid(id))) {
            DoctorPatchResponse.setSuccess(false);
            DoctorPatchResponse.setErrors(Collections.singletonList(ApplicationConstants.INVALID_UUID));
            return new ResponseEntity<>(DoctorPatchResponse, HttpStatus.BAD_REQUEST);
        }
        Optional<DoctorEntity> doctorOpt = doctorRepository.findById(UUID.fromString(id));
        if (doctorOpt.isEmpty()) {
            DoctorPatchResponse.setSuccess(false);
            DoctorPatchResponse.setErrors(Collections.singletonList(ApplicationConstants.DOCTORS_NOT_FOUND));
            return new ResponseEntity<>(DoctorPatchResponse, HttpStatus.NOT_FOUND);
        }
        DoctorEntity doctorEntity = doctorOpt.get();

        try {
            JsonNode doctorNode = objectMapper.convertValue(doctorEntity, JsonNode.class);
            JsonNode patchedNode = patch.apply(doctorNode);
            DoctorEntity patchedEntity = objectMapper.treeToValue(patchedNode, DoctorEntity.class);
            DoctorEntity savedDoctor = doctorRepository.save(patchedEntity);
            DoctorDto doctorDto = DoctorDto.builder()
                    .id(savedDoctor.getId())
                    .firstName(savedDoctor.getFirstName())
                    .lastName(savedDoctor.getLastName())
                    .department(savedDoctor.getDepartment())
                    .build();
            DoctorPatchResponse.setSuccess(true);
            DoctorPatchResponse.setDoctor(doctorDto);
            return new ResponseEntity<>(DoctorPatchResponse, HttpStatus.OK);
        } catch (Exception e) {
            DoctorPatchResponse.setSuccess(false);
            return new ResponseEntity<>(DoctorPatchResponse, HttpStatus.NOT_FOUND);
        }
    }
}
