package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorPatchResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;

/**
 * This class handles exceptions globally for the application.
 * It catches specific exceptions and returns appropriate HTTP responses
 * with error messages in a standard format using DoctorPatchResponse.
 * Global exception handler class
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * method to handle invalid userinput
     *
     * @param invalidUserInputException-exception class
     * @return appropriate response
     */
    @ExceptionHandler(InvalidUserInputException.class)
    public ResponseEntity<DoctorResponse> handleInvalidUserInputExcetion(InvalidUserInputException invalidUserInputException) {
        DoctorResponse errorResponseDto = DoctorResponse.builder()
                .success(false).errors(List.of(invalidUserInputException.getMessage())).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    /**
     * Handles InvalidUuidException when an invalid UUID is provided.
     * method to handle Invalid request body
     *
     * @param invalidUuidException the exception thrown
     * @return ResponseEntity with error message and HTTP 400 (Bad Request)
     * @return appropriate response
     */
    @ExceptionHandler(InvalidUuidExcetion.class)
    public ResponseEntity<DoctorPatchResponse> handleInvalidUuid(InvalidUuidExcetion invalidUuidException) {
        DoctorPatchResponse doctorPatchResponse =
                DoctorPatchResponse.builder()
                        .success(false)
                        .errors(Collections.singletonList(invalidUuidException.getMessage()))
                        .build();
        return new ResponseEntity<>(doctorPatchResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DoctorResponse> handleInvalidRequestBody(HttpMessageNotReadableException invalidUserInputException) {
        DoctorResponse errorResponseDto = DoctorResponse.builder()
                .success(false).errors(List.of(ApplicationConstants.INVALID_REQUESTBODY_ERROR)).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<DoctorResponse> handleInternalServerError(Exception exception) {
        DoctorResponse errorResponseDto = DoctorResponse.builder().success(false)
                .errors(List.of(ApplicationConstants.SERVER_ERROR + exception.getMessage())).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }

    /**
     * Handles handleEmptyUuidException when the UUID is missing/empty from the request.
     *
     * @param emptyUuidException-exception class
     * @return appropriate response
     */
    @ExceptionHandler(EmptyUuidException.class)
    public ResponseEntity<DoctorResponse> handleEmptyUuidException(EmptyUuidException emptyUuidException) {
        DoctorResponse responseDto = DoctorResponse.builder().success(false).errors(List.of(emptyUuidException.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    /**
     * Handles InvalidJsonOperation when an unsupported JSON Patch operation is used (like add/remove).
     * method to handle Invalid uuid
     *
     * @param invalidJsonOperation the exception thrown
     * @return ResponseEntity with error message and HTTP 400 (Bad Request)
     * @return appropriate response
     */

    @ExceptionHandler(InvalidJsonOperationException.class)
    public ResponseEntity<DoctorPatchResponse> handleInvalidJsonOperation(InvalidJsonOperationException invalidJsonOperation) {
        DoctorPatchResponse doctorPatchResponse = DoctorPatchResponse.builder()
                .success(false)
                .errors(invalidJsonOperation.getErrors())
                .build();
        return new ResponseEntity<>(doctorPatchResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles IdReplacementException when there's an attempt to modify the doctor's ID using JSON Patch.
     * method to handle Doctornotfound exception
     *
     * @param idReplacementException the exception thrown
     * @return ResponseEntity with error message and HTTP 400 (Bad Request)
     * @return appropriate response
     */

    @ExceptionHandler(IdReplacementException.class)
    public ResponseEntity<DoctorPatchResponse> handleInvalidIdReplacementOperation(IdReplacementException idReplacementException) {
        DoctorPatchResponse doctorPatchResponse = DoctorPatchResponse.builder()
                .success(false)
                .errors(Collections.singletonList(ApplicationConstants.ID_REPLACEMENT_NOT_ALLOWED))
                .build();
        return new ResponseEntity<>(doctorPatchResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<DoctorResponse> handleDoctorNotFound(DataNotFoundException dataNotFoundException) {
        DoctorResponse responseDto = DoctorResponse.builder().success(false).errors(List.of(dataNotFoundException.getMessage() + dataNotFoundException.getId())).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    @ExceptionHandler(JsonPatchProcessingException.class)
    public ResponseEntity<Object> handleJsonPatchError(JsonPatchProcessingException jsonPatchProcessingException) {
        DoctorPatchResponse doctorPatchResponse = DoctorPatchResponse.builder()
                .success(false)
                .errors(Collections.singletonList(jsonPatchProcessingException.getMessage()))
                .build();
        return new ResponseEntity<>(doctorPatchResponse, HttpStatus.NOT_FOUND);
    }
}
