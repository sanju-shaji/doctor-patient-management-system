package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientDeletionService;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

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
        when(patientDeletionService.deletePatientById(patientIdString)).thenReturn(ResponseEntity.ok(patientResponse));
        ResponseEntity<BaseResponse> response = patientDeletionController.ResponseDto(patientIdString);
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().isSuccess());
        assertEquals(List.of(TestApplicationConstants.PATIENT_DELETED_SUCCESSFULLY), response.getBody().getMessages());
        verify(patientDeletionService, times(1)).deletePatientById(patientIdString);
    }

    @Test
    void testDeletePatientById_DataNotFoundException() throws Exception {
        UUID patientId = UUID.randomUUID();
        String patientIdString = patientId.toString();
        when(patientDeletionService.deletePatientById(patientIdString)).thenThrow(new DataNotFoundException(TestApplicationConstants.NO_PATIENT_FOUND_WITH_ID));
        try {
            patientDeletionController.ResponseDto(patientIdString);
            fail(TestApplicationConstants.EXPECTED_DATA_NOT_FOUND_EXCEPTION);
        } catch (DataNotFoundException exception) {
            assertEquals(TestApplicationConstants.NO_PATIENT_FOUND_WITH_ID, exception.getMessage());
        }
        verify(patientDeletionService, times(1)).deletePatientById(patientIdString);
    }
}
