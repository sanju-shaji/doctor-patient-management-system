package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientDeletionService;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class PatientDeletionControllerTest {

    TestDataBuilder testDataBuilder;
    @Mock
    private PatientDeletionService patientDeletionService;

    @InjectMocks
    private PatientDeletionController patientDeletionController;

    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
    }

    /**
     * Method to testing Happy Path of createDoctor method
     * HTTP Status Code-200
     */
    @Test
    void testDeletePatientById_Success() throws Exception {
        UUID patientId = UUID.randomUUID();
        String patientIdString = patientId.toString();
        PatientResponse patientResponse = testDataBuilder.patientResponseBuilder(patientId);
        Mockito.when(patientDeletionService.deletePatientById(patientIdString)).thenReturn(ResponseEntity.ok(patientResponse));
        ResponseEntity<BaseResponse> response = patientDeletionController.ResponseDto(patientIdString);
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertTrue(response.getBody().isSuccess());
        Assertions.assertEquals(List.of(TestApplicationConstants.PATIENT_DELETED_SUCCESSFULLY), response.getBody().getMessages());
        Mockito.verify(patientDeletionService, Mockito.times(1)).deletePatientById(patientIdString);
    }

    @Test
    void testDeletePatientById_DataNotFoundException() throws Exception {
        UUID patientId = UUID.randomUUID();
        String patientIdString = patientId.toString();
        Mockito.when(patientDeletionService.deletePatientById(patientIdString)).thenThrow(new DataNotFoundException(TestApplicationConstants.NO_PATIENT_FOUND_WITH_ID));
        try {
            patientDeletionController.ResponseDto(patientIdString);
            Assertions.fail(TestApplicationConstants.EXPECTED_DATA_NOT_FOUND_EXCEPTION);
        } catch (DataNotFoundException exception) {
            Assertions.assertEquals(TestApplicationConstants.NO_PATIENT_FOUND_WITH_ID, exception.getMessage());
        }
        Mockito.verify(patientDeletionService, Mockito.times(1)).deletePatientById(patientIdString);
    }
}
