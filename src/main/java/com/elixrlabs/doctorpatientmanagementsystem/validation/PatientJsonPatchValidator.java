package com.elixrlabs.doctorpatientmanagementsystem.validation;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
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

    public PatientJsonPatchValidator(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    public List<String> validatePatch(JsonPatch patch, ObjectMapper objectMapper) throws Exception {
        List<String> errors = new ArrayList<>();
        JsonNode patchNode = objectMapper.valueToTree(patch);
        for (JsonNode operation : patchNode) {
            String operations = operation.get(ApplicationConstants.OPERATION).asText();
            String path = operation.get(ApplicationConstants.PATH).asText();
            JsonNode valueNode = operation.get(ApplicationConstants.VALUE);
            if (ApplicationConstants.ID.equalsIgnoreCase(path)) {
                String message = messageUtil.getMessage(MessageKeyEnum.MODIFICATION_OF_PATIENT_ID_IS_NOT_ALLOWED.getKey());
                errors.add(message);
            }
            if (!ApplicationConstants.REPLACE.equalsIgnoreCase(operations)) {
                String message = messageUtil.getMessage(MessageKeyEnum.ONLY_REPLACE_OPERATION_ARE_PERMITTED.getKey());
                errors.add(operations + ApplicationConstants.EMPTY_SPACE + message);
            }
            if (!ApplicationConstants.FIRSTNAME.equalsIgnoreCase(path) && !ApplicationConstants.LASTNAME.equalsIgnoreCase(path)) {
                String message = messageUtil.getMessage(MessageKeyEnum.FIRSTNAME_AND_LASTNAME_PATHS_ARE_ALLOWED.getKey());
                errors.add(message);
            }
            if (valueNode == null || valueNode.asText().trim().isEmpty()) {
                String message = messageUtil.getMessage(MessageKeyEnum.NULL_OR_EMPTY_VALUES_ARE_NOT_ALLOWED.getKey());
                errors.add(message);
            }
        }
        return errors;
    }
}
