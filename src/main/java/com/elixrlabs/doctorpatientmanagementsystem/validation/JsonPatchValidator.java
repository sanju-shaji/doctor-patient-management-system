package com.elixrlabs.doctorpatientmanagementsystem.validation;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidJsonOperationException;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for validating JSON Patch operations
 * before they are applied to the Doctor entity.
 * It checks for:
 * - Disallowed operations like 'add' and 'remove'
 * - Restricted paths like replacing the 'id' field
 */

@Component
public class JsonPatchValidator {

    private final MessageUtil messageUtil;
    private final PatientRepository patientRepository;

    public JsonPatchValidator(MessageUtil messageUtil, PatientRepository patientRepository) {
        this.messageUtil = messageUtil;
        this.patientRepository = patientRepository;
    }

    /**
     * Validates a JSON Patch request to ensure only allowed operations and paths are used.
     * <p>
     * - Rejects 'add' and 'remove' operations<br>
     * - Allows 'replace' operation only on firstName, lastName, and department fields
     *
     * @param patch the JSON Patch request with a list of operations
     * @throws InvalidJsonOperationException if any invalid operation or path is found
     */

    public void validateJsonOperations(JsonPatch patch) throws InvalidJsonOperationException {
        List<String> errors = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode patchNode = mapper.valueToTree(patch);
        for (JsonNode patchOperation : patchNode) {
            String operationType = patchOperation.get(ApplicationConstants.PATCH_OPERATION_KEY).asText();
            String path = patchOperation.get(ApplicationConstants.PATCH_PATH_KEY).asText();
            String Value = patchOperation.get(ApplicationConstants.PATCH_VALUE_KEY).asText();
            if (ApplicationConstants.PATCH_ADD_OPERATION.equalsIgnoreCase(operationType)) {
                String message = messageUtil.getMessage(MessageKeyEnum.ADD_OPERATION_NOT_ALLOWED.getKey(), path);
                errors.add(message);
            }
            if (ApplicationConstants.PATCH_REMOVE_OPERATION.equalsIgnoreCase(operationType)) {
                String message = messageUtil.getMessage(MessageKeyEnum.REMOVE_OPERATION_NOT_ALLOWED.getKey(), path);
                errors.add(message);
            }
            if (ApplicationConstants.PATCH_REPLACE_OPERATION.equalsIgnoreCase(operationType)
                    && !isAllowedReplacePath(path)) {
                String message = messageUtil.getMessage(MessageKeyEnum.REPLACE_NON_EXISTENT_FIELD_NOT_ALLOWED.getKey(), path);
                errors.add(message);
            }
            if (ApplicationConstants.PATCH_PATH_FIRST_NAME.equalsIgnoreCase(path) && patientRepository.existsByFirstNameIgnoreCase(Value)) {
                String message = messageUtil.getMessage(MessageKeyEnum.DUPLICATE_FIRST_NAME.getKey(), Value);
                errors.add(message);
                continue;
            }
            if (ApplicationConstants.PATCH_PATH_LAST_NAME.equalsIgnoreCase(path) && patientRepository.existsByLastNameIgnoreCase(Value)) {
                String message = messageUtil.getMessage(MessageKeyEnum.DUPLICATE_LAST_NAME.getKey(), Value);
                errors.add(message);
            }
        }
        if (!errors.isEmpty()) {
            throw new InvalidJsonOperationException(errors);
        }
    }

    /**
     * Checks if the given path is allowed for 'replace' operation.
     */
    private boolean isAllowedReplacePath(String path) {
        return path.equalsIgnoreCase(ApplicationConstants.PATCH_PATH_FIRST_NAME)
                || path.equalsIgnoreCase(ApplicationConstants.PATCH_PATH_LAST_NAME)
                || path.equalsIgnoreCase(ApplicationConstants.PATCH_PATH_DEPARTMENT);
    }
}
