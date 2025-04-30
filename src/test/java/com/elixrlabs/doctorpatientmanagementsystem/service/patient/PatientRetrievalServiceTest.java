package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;

/**
 * Unit tests for PatientRetrievalService
 * Uses Mockito for mocking dependencies and JUnit 5 for assertions
 */
@ExtendWith(MockitoExtension.class)
public class PatientRetrievalServiceTest {
    @Mock
    PatientRepository patientRepository;
    @Mock
    PatientValidation patientValidation;
    @Mock
    MessageUtil messageUtil;
    @InjectMocks
    PatientRetrievalService patientRetrievalService;
    TestDataBuilder testDataBuilder;

    @BeforeEach
    void setup() {
        testDataBuilder = new TestDataBuilder();
    }

    /**
     * Test case for successful patient retrieval when a valid UUID is provided and patient exists in the repository.
     */
    @Test
    void testGetPatientById_success() throws Exception {
        PatientModel patientModel = testDataBuilder.patientModelBuilder();
        PatientResponse expectedPatientResponse = testDataBuilder.getPatientByIdResponseBuilder();
        assert patientModel != null;
        Mockito.when(patientRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(patientModel));
        ResponseEntity<PatientResponse> patientData = patientRetrievalService.getPatientById(patientModel.getId().toString());
        assertEquals(HttpStatus.OK.value(), patientData.getStatusCode().value());
        assertEquals(expectedPatientResponse, patientData.getBody());
        verify(patientValidation, Mockito.times(1)).validatePatientId(Mockito.anyString());
        verify(patientRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
    }

    /**
     * Test case to verify the behaviour when an invalid UUID is provided
     */
    @Test
    void testGetPatientById_invalidUuid() throws Exception {
        doThrow(new InvalidUuidException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE)).when(patientValidation).validatePatientId(TestApplicationConstants.INVALID_UUID);
        PatientResponse expectedPatientResponse = testDataBuilder.invalidPatientResponseBuilder();
        try {
            ResponseEntity<PatientResponse> patientData = patientRetrievalService.getPatientById(TestApplicationConstants.INVALID_UUID);
            assertEquals(HttpStatus.BAD_REQUEST.value(), patientData.getStatusCode().value());
            assertEquals(expectedPatientResponse, patientData.getBody());
            verify(patientValidation, times(1)).validatePatientId(anyString());
            verify(patientRepository, never()).findById(any());
        } catch (InvalidUuidException invalidUuidException) {
            ResponseEntity.badRequest().body(PatientResponse.builder().success(false).errors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE)).build());
        }
    }

    /**
     * Test case to verify the behaviour when a valid UUID is provided but the patient is not found in the repository
     */
    @Test
    void testGetPatientById_PatientNotFound() throws Exception {
        PatientResponse expectedPatientResponse = testDataBuilder.invalidPatientResponseBuilder();
        doNothing().when(patientValidation).validatePatientId(TestApplicationConstants.UUID);
        Mockito.when(patientRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());
        try {
            ResponseEntity<PatientResponse> patientData = patientRetrievalService.getPatientById(TestApplicationConstants.UUID);
            assertEquals(HttpStatus.NOT_FOUND.value(), patientData.getStatusCode().value());
            assertEquals(expectedPatientResponse, patientData.getBody());
            verify(patientValidation, times(1)).validatePatientId(anyString());
            verify(patientRepository, never()).findById(any());
        } catch (DataNotFoundException dataNotFoundException) {
            ResponseEntity.badRequest().body(PatientResponse.builder().success(false).errors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE)).build());
        }
    }
}