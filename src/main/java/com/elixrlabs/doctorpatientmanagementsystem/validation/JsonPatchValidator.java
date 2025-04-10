package com.elixrlabs.doctorpatientmanagementsystem.validation;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.IdReplacementException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidJsonOperationException;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorPatchResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for validating JSON Patch operations
 * before they are applied to the Doctor entity.
 * It checks for:
 * - Disallowed operations like 'add' and 'remove'
 * - Restricted paths like replacing the 'id' field
 */
public class JsonPatchValidator {

    /**
     * This method validates the json patch and restrict the add ,remove operation and replacing the id
     *
     * @param patch the JSON Patch request containing a list of operations (e.g., replace, add)
     * @return it will return responseEntity with success message and realted message
     */
    public void validatejsonOperations(JsonPatch patch) throws InvalidJsonOperationException, IdReplacementException {
        List<String> errors = new ArrayList<>();
        DoctorPatchResponse doctorPatchResponse = new DoctorPatchResponse();
        JsonNode patchNode = null;
        ObjectMapper mapper = new ObjectMapper();
        patchNode = mapper.valueToTree(patch);
        for (JsonNode patchOperation : patchNode) {
            String operationType = patchOperation.get(ApplicationConstants.PATCH_OPERATION_KEY).asText();
            String path = patchOperation.get(ApplicationConstants.PATCH_PATH_KEY).asText();
            if (operationType.equalsIgnoreCase(ApplicationConstants.PATCH_ADD_OPERATION) || operationType.equalsIgnoreCase(ApplicationConstants.PATCH_REMOVE_OPERATION)) {
                errors.add(ApplicationConstants.ADD_REMOVE_OPERATION_NOT_ALLOWED + ApplicationConstants.COLON + operationType);
            }
            if (operationType.equalsIgnoreCase(ApplicationConstants.PATCH_REPLACE_OPERATION) && path.equalsIgnoreCase(ApplicationConstants.PATCH_PATH_ID)) {
                errors.add(ApplicationConstants.ID_REPLACEMENT_NOT_ALLOWED);
            }
        }
        if (!errors.isEmpty()) {
            throw new InvalidJsonOperationException(errors);
        }
    }
}
