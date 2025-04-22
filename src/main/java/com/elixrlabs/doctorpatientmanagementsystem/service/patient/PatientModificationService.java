package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatchPatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.validation.PatientJsonPatchValidator;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Service
public class PatientModificationService {
    private final PatientRepository patientRepository;
    private final ObjectMapper objectMapper;
    private final PatientValidation patientValidation;
    private final MessageUtil messageUtil;
    private final PatientJsonPatchValidator patientJsonPatchValidator;

    public PatientModificationService(PatientRepository patientRepository,
                                      ObjectMapper objectMapper,
                                      PatientValidation patientValidation,
                                      MessageUtil messageUtil,
                                      PatientJsonPatchValidator patientJsonPatchValidator) {
        this.patientRepository = patientRepository;
        this.objectMapper = objectMapper;
        this.patientValidation = patientValidation;
        this.messageUtil = messageUtil;
        this.patientJsonPatchValidator = patientJsonPatchValidator;
    }

    public ResponseEntity<PatchPatientResponse> applyPatch(String patientId, JsonPatch patch) throws Exception {
        PatchPatientResponse patchPatientResponse = new PatchPatientResponse();
        patientValidation.validatePatientId(patientId);
        Optional<PatientModel> patientModelOptional = patientRepository.findById(UUID.fromString(patientId));
        if (patientModelOptional.isEmpty()) {
            String message = messageUtil.getMessage(MessageKeyEnum.NO_PATIENT_FOUND.getKey(), patientId);
            throw new DataNotFoundException(message);
        }
        PatientModel patientModel = patientModelOptional.get();
        PatientDto patientDto = objectMapper.convertValue(patientModel, PatientDto.class);
        JsonNode patientNode = objectMapper.convertValue(patientDto, JsonNode.class);
        List<JsonNode> operations = new ArrayList<>();
        List<String> errors = patientJsonPatchValidator.validatePatch(patch, operations);
        boolean hasAnyUpdate = false;
        for (JsonNode operation : operations) {
            JsonPatch singlePatch = JsonPatch.fromJson(objectMapper.createArrayNode().add(operation));
            JsonNode patchedNode = singlePatch.apply(patientNode);
            PatientDto patchedDto = objectMapper.treeToValue(patchedNode, PatientDto.class);
            List<String> fieldErrors = patientValidation.validatePatients(patchedDto);
            if (fieldErrors.isEmpty()) {
                patientNode = patchedNode;
                hasAnyUpdate = true;
            } else {
                errors.add(fieldErrors.toString());
            }
        }
        boolean hasError = !errors.isEmpty();
        if (hasAnyUpdate && patientNode != null) {
            PatientDto finalpatchedDto = objectMapper.treeToValue(patientNode, PatientDto.class);
            PatientModel updatedModel = objectMapper.convertValue(finalpatchedDto, PatientModel.class);
            updatedModel.setId(patientModel.getId());
            PatientModel saved = patientRepository.save(updatedModel);
            PatientDto responseDto = PatientDto.builder()
                    .id(saved.getId())
                    .firstName(saved.getFirstName())
                    .lastName(saved.getLastName()).build();
            patchPatientResponse.setSuccess(!hasError);
            patchPatientResponse.setPatient(responseDto);
            patchPatientResponse.setErrors(errors.isEmpty() ? null : errors);
            return new ResponseEntity<>(patchPatientResponse, hasError ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
        } else {
            patchPatientResponse.setSuccess(false);
            patchPatientResponse.setErrors(errors);
        }
        return new ResponseEntity<>(patchPatientResponse, HttpStatus.BAD_REQUEST);
    }
}
