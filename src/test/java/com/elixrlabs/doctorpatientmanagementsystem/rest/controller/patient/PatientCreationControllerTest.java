package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientCreationService;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Unit test class for PatientCreationController
 */
@ExtendWith(MockitoExtension.class)
public class PatientCreationControllerTest {
    @Mock
    PatientCreationService patientCreationService;
    @InjectMocks
    PatientCreationController patientCreationController;
    TestDataBuilder testDataBuilder;

    /**
     * Initializes the TestDataBuilder before each test case
     * TestDataBuilder is used to provide test data for DTOs and Responses
     */
    @BeforeEach
    void setup() {
        testDataBuilder = new TestDataBuilder();
    }

    /**
     * Test case for successful patient creation through the controller
     * Validates that the Response body and status codes are correct and service method is invoked exactly once
     */
    @Test
    void testPatientCreationController_Success() throws Exception {
        PatientDto patient = testDataBuilder.patientDtoBuilder();
        PatientResponse expectedPatientResponse = testDataBuilder.patientResponseBuilder();
        Mockito.when(patientCreationService.createPatient(Mockito.any(PatientDto.class))).thenReturn(ResponseEntity.ok().body(expectedPatientResponse));
        ResponseEntity<PatientResponse> patientCreationResponse = patientCreationController.createPatient(patient);
        Assertions.assertEquals(expectedPatientResponse, patientCreationResponse.getBody());
        Assertions.assertNotNull(patientCreationResponse.getBody());
        Assertions.assertTrue(expectedPatientResponse.isSuccess());
        Assertions.assertEquals(HttpStatus.OK, patientCreationResponse.getStatusCode());
        Mockito.verify(patientCreationService, Mockito.times(1)).createPatient(patient);
    }

    /**
     * Test case for invalid input scenario in patient creation
     */
    @Test
    void testPatientCreationController_invalidInputs() throws Exception {
        PatientDto patient = testDataBuilder.patientDtoBuilder();
        Mockito.when(patientCreationService.createPatient(patient)).thenThrow(new InvalidUserInputException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        try {
            ResponseEntity<PatientResponse> patientCreationResponse = patientCreationController.createPatient(patient);
            Assertions.assertNotNull(patientCreationResponse.getBody());
            Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), patientCreationResponse.getStatusCode().value());
            Assertions.assertFalse(patientCreationResponse.getBody().isSuccess());
            Assertions.assertEquals(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE, patientCreationResponse.getBody().getErrors().get(0));
            Mockito.verify(patientCreationService, Mockito.times(1)).createPatient(patient);
        } catch (InvalidUserInputException invalidUserInputException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PatientResponse.builder().success(false).errors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE)));
        }
    }
}