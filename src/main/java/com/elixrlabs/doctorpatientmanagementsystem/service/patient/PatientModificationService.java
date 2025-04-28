package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidJsonOperationException;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatchPatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.validation.JsonPatchValidator;
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

/**
 * This class responsible for handling modifications to Patient entities,
 * specifically applying JSON Patch operations to partially update patient data.
 */
@Service
public class PatientModificationService {

    private final PatientRepository patientRepository;
    private final ObjectMapper objectMapper;
    private final PatientValidation patientValidation;
    private final MessageUtil messageUtil;

    public PatientModificationService(PatientRepository patientRepository,
                                      ObjectMapper objectMapper,
                                      PatientValidation patientValidation,
                                      MessageUtil messageUtil) {
        this.patientRepository = patientRepository;
        this.objectMapper = objectMapper;
        this.patientValidation = patientValidation;
        this.messageUtil = messageUtil;
    }

    public ResponseEntity<PatchPatientResponse> applyPatch(String patientId, JsonPatch patch) throws Exception {
        PatchPatientResponse patchPatientResponse = new PatchPatientResponse();
        patientValidation.validatePatientId(patientId);
        Optional<PatientModel> patientModelOptional = patientRepository.findById(UUID.fromString(patientId));
        if (patientModelOptional.isEmpty()) {
            String message = messageUtil.getMessage(MessageKeyEnum.NO_PATIENT_FOUND.getKey(), patientId);
            throw new DataNotFoundException(message);
        }
        JsonPatchValidator jsonPatchValidator = new JsonPatchValidator(messageUtil);
        jsonPatchValidator.validateJsonOperations(patch);
        PatientModel patientModel = patientModelOptional.get();
        PatientDto patientDto = objectMapper.convertValue(patientModel, PatientDto.class);
        JsonNode patientNode = objectMapper.convertValue(patientDto, JsonNode.class);
        JsonNode patchedNode = patch.apply(patientNode);
        PatientDto patchedDto = objectMapper.treeToValue(patchedNode, PatientDto.class);
        List<String> validationErrors = patientValidation.validatePatients(patchedDto);
        if (!validationErrors.isEmpty()) {
            throw new InvalidJsonOperationException(validationErrors);
        }
        PatientModel patchedModel = objectMapper.convertValue(patchedDto, PatientModel.class);
        patchedModel.setId(patchedModel.getId());
        if (patchedModel.getFirstName().equalsIgnoreCase(patientModel.getFirstName())
                && patchedModel.getLastName().equalsIgnoreCase(patientModel.getLastName())) {
            patchPatientResponse.setSuccess(true);
            patchPatientResponse.setPatient(new PatientDto(patientModel));
            return new ResponseEntity<>(patchPatientResponse, HttpStatus.OK);
        }
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
