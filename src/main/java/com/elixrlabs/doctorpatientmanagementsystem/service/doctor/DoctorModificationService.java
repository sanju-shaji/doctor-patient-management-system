package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.*;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorPatchResponse;

import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.ResponseBuilder;
import com.elixrlabs.doctorpatientmanagementsystem.validation.JsonPatchValidator;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;

import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private final ResponseBuilder responseBuilder;
    private final MessageUtil messageUtil;

    public DoctorModificationService(DoctorRepository doctorRepository,
                                     ObjectMapper objectMapper,
                                     DoctorValidation doctorValidation,
                                     ResponseBuilder responseBuilder, MessageUtil messageUtil) {
        this.doctorRepository = doctorRepository;
        this.objectMapper = objectMapper;
        this.doctorValidation = doctorValidation;
        this.responseBuilder = responseBuilder;
        this.messageUtil = messageUtil;
    }

    /**
     * This method validate the doctorId and return the updated doctorDetails
     *
     * @param doctorId it will help to validate doctorId.The id of the doctor to update
     * @param patch    the details need to be changed
     * @return it will return  the ResponseEntity<DoctorPatchResponse>  to the DoctorModificationController
     */
    public ResponseEntity<DoctorPatchResponse> applyPatchToDoctor(String doctorId, JsonPatch patch) throws Exception {
        DoctorEntity doctorToUpdate = validateAndFetchDoctor(doctorId, patch);
        DoctorDto patchedDto = applyPatchAndConvertToDto(doctorToUpdate, patch);
        doctorValidation.validateDoctorDetails(patchedDto);
        DoctorEntity patchedEntity = objectMapper.convertValue(patchedDto, DoctorEntity.class);
        DoctorEntity savedDoctor = doctorRepository.save(patchedEntity);
        DoctorDto doctorDto = new DoctorDto(savedDoctor);
        return responseBuilder.buildSuccessPatchResponse(doctorDto);
    }

    /**
     * This method validates the empty/null/blank UUID,
     * and it validates the jsonOperations like add/remove
     */
    private DoctorEntity validateAndFetchDoctor(String doctorId, JsonPatch patch) throws InvalidUuidException, DataNotFoundException {
        doctorValidation.validateDoctorId(doctorId);
        Optional<DoctorEntity> doctorEntityOptional = doctorRepository.findById(UUID.fromString(doctorId));
        if (doctorEntityOptional.isEmpty()) {
            throw new DataNotFoundException(ApplicationConstants.DOCTORS_NOT_FOUND, UUID.fromString(doctorId));
        }
        JsonPatchValidator jsonPatchValidator = new JsonPatchValidator(messageUtil);
        jsonPatchValidator.validateJsonOperations(patch);
        return doctorEntityOptional.get();
    }

    /**
     * \
     * Here we are converting entity -> dto -> jsonNode ->patchedNode -> entity ->dto,
     * and we are saves the updated doctorDetails in the db
     *
     * @param doctorEntity it will store the doctorDetails by using doctorId
     * @param patch        it will store the doctorDetails from the requestBody
     * @return the updated DoctorDto with the patched values
     * @throws JsonPatchException      if there is a problem while applying the patch
     * @throws JsonProcessingException if there is an error in JSON processing
     */
    private DoctorDto applyPatchAndConvertToDto(DoctorEntity doctorEntity, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        DoctorDto doctorDto = new DoctorDto(doctorEntity);
        JsonNode doctorNode = objectMapper.convertValue(doctorDto, JsonNode.class);
        JsonNode patchedNode = patch.apply(doctorNode);
        return objectMapper.treeToValue(patchedNode, DoctorDto.class);
    }
}
