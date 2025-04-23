package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientDeletionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class PatientDeletionControllerTest {

    @Mock
    private PatientDeletionService patientDeletionService;
    @InjectMocks
    private PatientDeletionController patientDeletionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method to testing Happy Path of createDoctor method
     * HTTP Status Code-200
     */
    @Test
    void testDeletePatientById_Success() throws Exception {
        UUID patientId = UUID.randomUUID();
        String patientIdString = patientId.toString();
        BaseResponse mockResponse = new BaseResponse();
        mockResponse.setMessages(List.of(TestApplicationConstants.PATIENT_DELETED_SUCCESSFULLY));
        ResponseEntity<BaseResponse> expectedResponse = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        when(patientDeletionService.deletePatientById(patientIdString)).thenReturn(expectedResponse);
        ResponseEntity<BaseResponse> response = patientDeletionController.ResponseDto(patientIdString);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(patientDeletionService, times(1)).deletePatientById(patientIdString);
    }

    @Test
    void testDeletePatientById_DataNotFoundException() throws Exception {
        UUID patientId = UUID.randomUUID();
        String patientIdString = patientId.toString();
        String errorMessage = TestApplicationConstants.NO_PATIENT_FOUND_WITH_ID + patientIdString;
        when(patientDeletionService.deletePatientById(patientIdString)).thenThrow(new DataNotFoundException(errorMessage));
        try {
            patientDeletionController.ResponseDto(patientIdString);
            assert false : TestApplicationConstants.EXPECTED_DATA_NOT_FOUND_EXCEPTION;
        } catch (DataNotFoundException exception) {
            assert exception.getMessage().contains(patientIdString);
        }
        verify(patientDeletionService, times(1)).deletePatientById(patientIdString);
    }
}
