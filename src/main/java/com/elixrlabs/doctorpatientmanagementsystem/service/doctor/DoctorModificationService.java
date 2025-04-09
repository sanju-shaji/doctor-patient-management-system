package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.MissingUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorPatchResponse;

import com.elixrlabs.doctorpatientmanagementsystem.validation.JsonPatchValidator;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;

import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
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
//    private final DoctorValidation doctorValidation;

    public DoctorModificationService(DoctorRepository doctorRepository, ObjectMapper objectMapper, DoctorValidation doctorValidation, DoctorPatchResponse doctorPatchResponse) {
        this.doctorRepository = doctorRepository;
        this.objectMapper = objectMapper;
      //  this.doctorValidation = doctorValidation;
    }

    /**
     * This method validate the doctorId and return the updated doctorDetails
     * @param doctorId it will helps to validate doctorId.The id of the doctor to update
     * @param patch    the details need to be changed
     * @return it will return  the ResponseEntity<DoctorPatchResponse>  to the DoctorModificationController
     */
    public ResponseEntity<DoctorPatchResponse> applyPatchToDoctor(String doctorId, JsonPatch patch) throws Exception, MissingUuidException, InvalidUuidException {

        DoctorPatchResponse doctorPatchResponse = new DoctorPatchResponse();
       DoctorValidation doctorValidation=new DoctorValidation();
       doctorValidation.validatePatchDoctor(doctorId);
        JsonPatchValidator jsonPatchValidator=new JsonPatchValidator();
        jsonPatchValidator.validatejsonOperations(patch);
        Optional<DoctorEntity> doctorEntityOptional = doctorRepository.findById(UUID.fromString(doctorId));
        if (doctorEntityOptional.isEmpty()) {
            throw new DataNotFoundException(ApplicationConstants.DOCTORS_NOT_FOUND);
        }
        try {
            DoctorDto patchedDto = applyPatchAndConvertToDto(doctorEntityOptional.get(), patch);
            List<String> errorMessageList = doctorValidation.validatePostDoctor(patchedDto);
            if (!errorMessageList.isEmpty()) {
                doctorPatchResponse.setSuccess(false);
                doctorPatchResponse.setErrors(errorMessageList);
                return new ResponseEntity<>(doctorPatchResponse, HttpStatus.BAD_REQUEST);
            }
            DoctorEntity patchedEntity = objectMapper.convertValue(patchedDto, DoctorEntity.class);
            DoctorEntity savedDoctor = doctorRepository.save(patchedEntity);
            DoctorDto doctorDto = DoctorDto.builder()
                    .id(savedDoctor.getId())
                    .firstName(savedDoctor.getFirstName())
                    .lastName(savedDoctor.getLastName())
                    .department(savedDoctor.getDepartment())
                    .build();
            doctorPatchResponse.setSuccess(true);
            doctorPatchResponse.setDoctor(doctorDto);
            return new ResponseEntity<>(doctorPatchResponse, HttpStatus.OK);
        } catch (JsonPatchException | JsonProcessingException e) {
            doctorPatchResponse.setSuccess(false);
            doctorPatchResponse.setErrors(Collections.singletonList(e.getMessage()));
            return new ResponseEntity<>(doctorPatchResponse, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * \
     * Here we are converting entity -> dto -> jsonNode ->patchedNode -> entity ->dto
     * and we are save the updated doctorDetails in the db
     *
     * @param doctorEntity it will stores the doctorDetails by using doctorId
     * @param patch        it will stores the doctorDetails from the requestBody
     * @return the updated DoctorDto with the patched values
     * @throws JsonPatchException      if there is a problem while applying the patch
     * @throws JsonProcessingException if there is an error in JSON processing
     */
    private DoctorDto applyPatchAndConvertToDto(DoctorEntity doctorEntity, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        DoctorDto doctorDto = DoctorDto.builder()
                .id(doctorEntity.getId())
                .firstName(doctorEntity.getFirstName())
                .lastName(doctorEntity.getLastName())
                .department(doctorEntity.getDepartment())
                .build();
        JsonNode doctorNode = objectMapper.convertValue(doctorDto, JsonNode.class);
        JsonNode patchedNode = patch.apply(doctorNode);
        return objectMapper.treeToValue(patchedNode, DoctorDto.class);
    }
}
