package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.JsonPatchProcessingException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.PatientValidationException;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatchPatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.validation.PatientJsonPatchValidator;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientModificationService {
    private final PatientRepository patientRepository;
    private final ObjectMapper objectMapper;
    private final PatientValidation patientValidation;
    private final PatientJsonPatchValidator jsonPatchValidator;
    private final MessageUtil messageUtil;

    public PatientModificationService(PatientRepository patientRepository,
                                      ObjectMapper objectMapper,
                                      PatientValidation patientValidation,
                                      PatientJsonPatchValidator jsonPatchValidator, MessageUtil messageUtil) {
        this.patientRepository = patientRepository;
        this.objectMapper = objectMapper;
        this.patientValidation = patientValidation;
        this.jsonPatchValidator = jsonPatchValidator;
        this.messageUtil = messageUtil;
    }

    public ResponseEntity<PatchPatientResponse> applyPatch(String patientId, JsonPatch patch) throws Exception {
        PatchPatientResponse patchPatientResponse = new PatchPatientResponse();
        patientValidation.validatePatientId(patientId);
        Optional<PatientModel> patientModelOptional = patientRepository.findById(UUID.fromString(patientId));
        if (patientModelOptional.isEmpty()) {
            String message = messageUtil.getMessage(MessageKeyEnum.NO_PATIENT_FOUND.getKey());
            throw new DataNotFoundException(message, UUID.fromString(patientId));
        }
        List<String> patchErrors = jsonPatchValidator.validatePatch(patch, objectMapper);
        if (!patchErrors.isEmpty()) {
            String message = messageUtil.getMessage(MessageKeyEnum.PATCH_REQUEST_FAILED.getKey());
            throw new JsonPatchProcessingException(message, patchErrors);
        }
        PatientModel patientModel = patientModelOptional.get();
        //JsonNode patchNode = objectMapper.valueToTree(patch);
        PatientDto patientDto = objectMapper.convertValue(patientModel, PatientDto.class);
        JsonNode patientNode = objectMapper.convertValue(patientDto, JsonNode.class);
        JsonNode patchedNode = patch.apply(patientNode);
        PatientDto patchedDto = objectMapper.treeToValue(patchedNode, PatientDto.class);
        List<String> validationErrors = patientValidation.validatePatients(patchedDto);
        if (!validationErrors.isEmpty()) {
            String message = messageUtil.getMessage(MessageKeyEnum.NULL_OR_EMPTY_VALUES_ARE_NOT_ALLOWED.getKey());
            throw new PatientValidationException(message);
        }
        PatientModel patchedModel = objectMapper.convertValue(patchedDto, PatientModel.class);
        patchedModel.setId(patchedModel.getId());
        PatientModel savedPatient = patientRepository.save(patchedModel);
        PatientDto responseDto = PatientDto.builder()
                .id(savedPatient.getId())
                .firstName(savedPatient.getFirstName())
                .lastName(savedPatient.getLastName()).build();
        patchPatientResponse.setSuccess(true);
        patchPatientResponse.setPatient(responseDto);
        return new ResponseEntity<>(patchPatientResponse, HttpStatus.OK);
    }
}
