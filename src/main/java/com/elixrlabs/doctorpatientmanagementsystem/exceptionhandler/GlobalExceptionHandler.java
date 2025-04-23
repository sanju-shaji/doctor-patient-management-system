package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorPatchResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.DoctorPatientAssignmentResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatchPatientResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

/**
 * This class handles exceptions globally for the application.
 * It catches specific exceptions and returns appropriate HTTP responses
 * with error messages in a standard format using DoctorPatchResponse.
 * Global exception handler class
 */
@RestControllerAdvice
@RequiredArgsConstructor

@ControllerAdvice
public class GlobalExceptionHandler {
    private final MessageUtil messageUtil;

    /**
     * method to handle invalid userInput
     *
     * @param invalidUserInputException-exception class
     * @return appropriate response
     */

    @ExceptionHandler(InvalidUserInputException.class)
    public ResponseEntity<DoctorResponse> handleInvalidUserInputException(InvalidUserInputException invalidUserInputException) {
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
     */
    @ExceptionHandler(InvalidUuidException.class)
    public ResponseEntity<DoctorPatchResponse> handleInvalidUuid(InvalidUuidException invalidUuidException) {
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
                .success(false).errors(List.of(messageUtil.getMessage(MessageKeyEnum.INVALID_REQUEST_BODY_ERROR.getKey()))).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    /**
     * method to handle server side exceptions
     *
     * @param exception-exception class
     * @return appropriate response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DoctorResponse> handleInternalServerError(Exception exception) {
        DoctorResponse errorResponseDto = DoctorResponse.builder().success(false)
                .errors(List.of(messageUtil.getMessage(MessageKeyEnum.SERVER_ERROR.getKey()) + exception.getMessage())).build();
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
     * method to handle DoctorNotFound exception
     *
     * @return ResponseEntity with error message and HTTP 400 (Bad Request)
     */

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<DoctorResponse> handleDoctorNotFound(DataNotFoundException dataNotFoundException) {
        DoctorResponse responseDto = DoctorResponse.builder().success(false).errors(List.of(dataNotFoundException.getMessage())).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    /**
     * method to handle invalid url provided by user
     *
     * @param missingServletRequestParameterException-exception class
     * @return appropriate response
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    ResponseEntity<DoctorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException missingServletRequestParameterException) {
        DoctorResponse responseDto = DoctorResponse.builder().success(false).errors(List.of(ApplicationConstants.INVALID_URL)).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    /**
     * method to handle PatientAlreadyAssignedDoctor exception
     *
     * @param patientAlreadyAssignedException-exception class
     * @return appropriate response
     */
    @ExceptionHandler(PatientAlreadyAssignedException.class)
    public ResponseEntity<BaseResponse> handlePatientAlreadyAssigned(PatientAlreadyAssignedException patientAlreadyAssignedException) {
        BaseResponse baseResponse = BaseResponse.builder().success(false).errors(List.of(patientAlreadyAssignedException.getMessage())).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(baseResponse);
    }

    @ExceptionHandler(JsonPatchProcessingException.class)
    public ResponseEntity<Object> handleJsonPatchError(JsonPatchProcessingException jsonPatchProcessingException) {
        DoctorPatchResponse doctorPatchResponse = DoctorPatchResponse.builder()
                .success(false)
                .errors(Collections.singletonList(jsonPatchProcessingException.getMessage()))
                .build();
        return new ResponseEntity<>(doctorPatchResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * method to handle PatientValidation exception
     *
     * @param patientValidationException-exception class
     * @return appropriate response
     */
    @ExceptionHandler(PatientValidationException.class)
    public ResponseEntity<PatchPatientResponse> handlePatientValidation(PatientValidationException patientValidationException) {
        PatchPatientResponse patchPatientResponse = new PatchPatientResponse();
        patchPatientResponse.setSuccess(false);
        patchPatientResponse.setErrors(List.of(patientValidationException.getMessage()));
        return new ResponseEntity<>(patchPatientResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * method to handle PatientNotFoundException exception
     *
     * @param patientNotFoundException-exception class
     * @return appropriate response
     */
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<PatientResponseDto> handlePatientNotFound(PatientNotFoundException patientNotFoundException) {
        PatientResponseDto patientResponseDto = PatientResponseDto.builder()
                .success(false)
                .errors(List.of(ApplicationConstants.NO_PATIENTS_FOUND))
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(patientResponseDto);
    }

    /**
     * Handles InvalidAssignmentDataException and returns a list of validation errors.
     *
     * @param invalidAssignmentDataException the exception containing validation error messages
     * @return a response with error messages and HTTP 404 status
     */
    @ExceptionHandler(InvalidAssignmentDataException.class)
    public ResponseEntity<BaseResponse> handleDoctorPatientNotFound(InvalidAssignmentDataException invalidAssignmentDataException) {
        BaseResponse baseResponse = BaseResponse.builder()
                .success(false)
                .errors(invalidAssignmentDataException.getErrors())
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UuidValidationException.class)
    public ResponseEntity<BaseResponse> handleUuidValidationException(UuidValidationException uuidValidationException) {
        BaseResponse baseResponse = BaseResponse.builder()
                .success(false)
                .errors(uuidValidationException.getErrors())
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoctorAlreadyUnassignedException.class)
    public ResponseEntity<BaseResponse> handleDoctorAlreadyUnassigned(DoctorAlreadyUnassignedException doctorAlreadyUnassignedException){
        BaseResponse baseResponse=BaseResponse.builder()
                .success(false)
                .errors(List.of(doctorAlreadyUnassignedException.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(baseResponse);
    }
}
