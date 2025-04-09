package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorPatchResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

/**
 * This class handles exceptions globally for the application.
 * It catches specific exceptions and returns appropriate HTTP responses
 * with error messages in a standard format using DoctorPatchResponse.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles InvalidUuidException when an invalid UUID is provided.
     *
     * @param invalidUuidException the exception thrown
     * @return ResponseEntity with error message and HTTP 400 (Bad Request)
     */
    @ExceptionHandler(InvalidUuidException.class)
    public ResponseEntity<DoctorPatchResponse> invalidUuid(InvalidUuidException invalidUuidException) {
        DoctorPatchResponse doctorPatchResponse =
                DoctorPatchResponse.builder()
                        .success(false)
                        .errors(Collections.singletonList(invalidUuidException.getMessage())).build();
        return new ResponseEntity<>(doctorPatchResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles DataNotFoundException when a doctor is not found in the database.
     *
     * @param dataNotFoundException the exception thrown
     * @return ResponseEntity with error message and HTTP 404 (Not Found)
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<DoctorPatchResponse> doctotNotFound(DataNotFoundException dataNotFoundException) {
        DoctorPatchResponse doctorPatchResponse =
                DoctorPatchResponse.builder()
                        .success(false)
                        .errors(Collections.singletonList(dataNotFoundException.getMessage()))
                        .build();
        return new ResponseEntity<>(doctorPatchResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles MissingUuuidException when the UUID is missing from the request.
     *
     * @param missingUuuidException the exception thrown
     * @return ResponseEntity with error message and HTTP 400 (Bad Request)
     */
    @ExceptionHandler(MissingUuidException.class)
    public ResponseEntity<DoctorPatchResponse> missingUuid(MissingUuidException missingUuuidException) {
        DoctorPatchResponse doctorPatchResponse =
                DoctorPatchResponse.builder()
                        .success(false)
                        .errors(Collections.singletonList(missingUuuidException.getMessage()))
                        .build();
        return new ResponseEntity<>(doctorPatchResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles InvalidJsonOperation when an unsupported JSON Patch operation is used (like add/remove).
     *
     * @param invalidJsonOperation the exception thrown
     * @return ResponseEntity with error message and HTTP 400 (Bad Request)
     */

    @ExceptionHandler(InvalidJsonOperationException.class)
    public ResponseEntity<DoctorPatchResponse> invalidJsonOperation(InvalidJsonOperationException invalidJsonOperation) {
        DoctorPatchResponse doctorPatchResponse =
                DoctorPatchResponse.builder()
                        .success(false)
                        .errors(Collections.singletonList(ApplicationConstants.ADD_REMOVE_OPERATION_NOT_ALLOWED))
                        .build();
        return new ResponseEntity<>(doctorPatchResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles IdReplacementException when there's an attempt to modify the doctor's ID using JSON Patch.
     *
     * @param idReplacementException the exception thrown
     * @return ResponseEntity with error message and HTTP 400 (Bad Request)
     */

    @ExceptionHandler(IdReplacementException.class)
    public ResponseEntity<DoctorPatchResponse> invalidIdReplacementOperation(IdReplacementException idReplacementException) {
        DoctorPatchResponse doctorPatchResponse =
                DoctorPatchResponse.builder()
                        .success(false)
                        .errors(Collections.singletonList(ApplicationConstants.ID_REPLACEMENT_NOT_ALLOWED))
                        .build();
        return new ResponseEntity<>(doctorPatchResponse, HttpStatus.BAD_REQUEST);
    }

}
