package com.elixrlabs.doctorpatientmanagementsystem.validation;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidJsonOperationException;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JsonPatchValidator {

    private final MessageUtil messageUtil;

    public JsonPatchValidator(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    public void validateJsonOperations(JsonPatch patch) throws InvalidJsonOperationException {
        List<String> errors = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode patchNode = mapper.valueToTree(patch);

        for (JsonNode patchOperation : patchNode) {
            String operationType = patchOperation.get(ApplicationConstants.PATCH_OPERATION_KEY).asText();
            String path = patchOperation.get(ApplicationConstants.PATCH_PATH_KEY).asText();

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
        }
        if (!errors.isEmpty()) {
            throw new InvalidJsonOperationException(errors);
        }
    }

    private boolean isAllowedReplacePath(String path) {
        return path.equalsIgnoreCase(ApplicationConstants.PATCH_PATH_FIRST_NAME)
                || path.equalsIgnoreCase(ApplicationConstants.PATCH_PATH_LAST_NAME)
                || path.equalsIgnoreCase(ApplicationConstants.PATCH_PATH_DEPARTMENT);
    }
}
