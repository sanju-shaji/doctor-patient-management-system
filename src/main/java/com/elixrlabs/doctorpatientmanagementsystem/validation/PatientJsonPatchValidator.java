package com.elixrlabs.doctorpatientmanagementsystem.validation;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Validate a Json Patch request to ensure only allowed operations and paths are used
 */
@Component
public class PatientJsonPatchValidator {
    private final MessageUtil messageUtil;
    private final PatientRepository patientRepository;

    public PatientJsonPatchValidator(MessageUtil messageUtil, PatientRepository patientRepository) {
        this.messageUtil = messageUtil;
        this.patientRepository = patientRepository;
    }


    public List<String> validatePatch(JsonPatch patch, List<JsonNode> validOperations) {
        List<String> errors = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode patchNode = mapper.valueToTree(patch);
        for (JsonNode patchOperation : patchNode) {
            String operationType = patchOperation.get(ApplicationConstants.PATCH_OPERATION_KEY).asText();
            String path = patchOperation.get(ApplicationConstants.PATCH_PATH_KEY).asText();
            String Value = patchOperation.get(ApplicationConstants.PATCH_VALUE_KEY).asText();
            if (!ApplicationConstants.PATCH_REPLACE_OPERATION.equalsIgnoreCase(operationType)) {
                String message = messageUtil.getMessage(MessageKeyEnum.ADD_OPERATION_NOT_ALLOWED.getKey(), path);
                errors.add(message);
                continue;
            }
            if (!isAllowedReplacePath(path)) {
                String message = messageUtil.getMessage(MessageKeyEnum.REPLACE_NON_EXISTENT_FIELD_NOT_ALLOWED.getKey(), path);
                errors.add(message);
                continue;
            }
            if (ApplicationConstants.PATCH_PATH_FIRST_NAME.equalsIgnoreCase(path) && patientRepository.existsByFirstNameIgnoreCase(Value)) {
                String message = messageUtil.getMessage(MessageKeyEnum.DUPLICATE_FIRST_NAME.getKey(), Value);
                errors.add(message);
                continue;
            }
            if (ApplicationConstants.PATCH_PATH_LAST_NAME.equalsIgnoreCase(path) && patientRepository.existsByLastNameIgnoreCase(Value)) {
                String message = messageUtil.getMessage(MessageKeyEnum.DUPLICATE_LAST_NAME.getKey(), Value);
                errors.add(message);
                continue;
            }
            validOperations.add(patchOperation);
        }
        return errors;
    }

    /**
     * Checks if the given path is allowed for 'replace' operation.
     */
    private boolean isAllowedReplacePath(String path) {
        return (ApplicationConstants.PATCH_PATH_FIRST_NAME.equalsIgnoreCase(path))
                || (ApplicationConstants.PATCH_PATH_LAST_NAME.equalsIgnoreCase(path))
                || (ApplicationConstants.PATCH_PATH_DEPARTMENT.equalsIgnoreCase(path));

    }
}
