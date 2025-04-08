package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Global exception handler class
 */
@RestControllerAdvice
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
     * method to handle Invalid request body
     *
     * @param invalidUserInputException-exception class
     * @return appropriate response
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DoctorResponse> handleInvalidRequestBody(HttpMessageNotReadableException invalidUserInputException) {
        DoctorResponse errorResponseDto = DoctorResponse.builder()
                .success(false).errors(List.of(ApplicationConstants.INVALID_REQUESTBODY_ERROR)).build();
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
                .errors(List.of(ApplicationConstants.SERVER_ERROR + exception.getMessage())).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
    }

    /**
     * method to handle empty uuid
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
     * method to handle Invalid uuid
     *
     * @param invalidUuidExcetion-exception class
     * @return appropriate response
     */
    @ExceptionHandler(InvalidUuidExcetion.class)
    public ResponseEntity<DoctorResponse> handleInvalidUuid(InvalidUuidExcetion invalidUuidExcetion) {
        DoctorResponse responseDto = DoctorResponse.builder().success(false)
                .errors(List.of(invalidUuidExcetion.getMessage())).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    /**
     * method to handle Doctornotfound exception
     *
     * @param doctorNotFoundException-exception class
     * @return appropriate response
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<DoctorResponse> handleDoctorNotFound(DataNotFoundException doctorNotFoundException) {
        DoctorResponse responseDto = DoctorResponse.builder().success(false).errors(List.of(doctorNotFoundException.getMessage() + doctorNotFoundException.getId())).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }
}
