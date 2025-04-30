package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.AssignedDoctorsToPatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.EmptyUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.DoctorPatientAssignmentResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for DoctorRetrievalService class to validate doctor retrieval functionality.
 */
@ExtendWith(MockitoExtension.class)
class DoctorRetrievalServiceTest {
    @Mock
    PatientRepository patientRepository;
    @Mock
    DoctorRepository doctorRepository;
    @Mock
    DoctorValidation doctorValidation;
    @Mock
    MessageUtil messageUtil;
    @InjectMocks
    DoctorRetrievalService doctorRetrievalService;
    TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
    }
// Test Cases for Get Doctors by id

    /**
     * Test Method for testing the happy path of getDoctorsById method of DoctorRetrievalService
     * HTTP Status code-200
     *
     * @throws InvalidUserInputException - if invalid user inputs are provided by the user
     */
    @Test
    void testGetDoctorsById_withValidInputs_returns200StatusAndValidDoctorResponse() throws Exception {
        DoctorEntity doctorEntity = testDataBuilder.doctorEntityBuilder();
        DoctorResponse expectedResponse = testDataBuilder.doctorResponseBuilder();
        assert doctorEntity != null;
        Mockito.when(doctorRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(doctorEntity));
        ResponseEntity<DoctorResponse> doctorData = doctorRetrievalService.getDoctorsById(doctorEntity.getId().toString());
        assertEquals(HttpStatus.OK.value(), doctorData.getStatusCode().value());
        assertEquals(expectedResponse, doctorData.getBody());
        Mockito.verify(doctorValidation, Mockito.times(1)).isInValidUUID(Mockito.anyString());
        Mockito.verify(doctorRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        Mockito.verify(messageUtil, Mockito.never()).getMessage(Mockito.anyString());
    }

    /**
     * Method to test invalidUUID user input for getDoctorByID method of DoctorRetrieval service class
     * HTTP Status Code-400
     *
     * @throws Exception if invalid user provide invalid uuid
     */
    @Test
    void testGetDoctorsById_withInvalidUUID_returns400StatusAndInvalidDoctorResponse() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        Mockito.when(doctorValidation.isInValidUUID(Mockito.anyString())).thenReturn(true);
        try {
            doctorRetrievalService.getDoctorsById(TestApplicationConstants.INVALID_UUID);
            Assertions.fail(TestApplicationConstants.EXPECTED_INVALID_UUID_EXCEPTION);
        } catch (InvalidUuidException invalidUuidException) {
            ResponseEntity<DoctorResponse> doctorData = ResponseEntity.badRequest().body(DoctorResponse.builder().success(false).errors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE)).build());
            assertEquals(HttpStatus.BAD_REQUEST.value(), doctorData.getStatusCode().value());
            assertEquals(expectedResponse, doctorData.getBody());
            Mockito.verify(doctorValidation, Mockito.times(1)).isInValidUUID(Mockito.anyString());
            Mockito.verify(doctorRepository, Mockito.never()).findById(Mockito.any(UUID.class));
        }
    }

    /**
     * Method to test if no user exist for the give doctor id
     * HTTP Status Code-404
     *
     * @throws Exception if no user is present in db
     */
    @Test
    void testGetDoctorsById_forUserNotFoundError_returns404StatusAndInvalidDoctorResponse() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        Mockito.when(doctorValidation.isInValidUUID(Mockito.anyString())).thenReturn(false);
        Mockito.when(doctorRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());
        try {
            doctorRetrievalService.getDoctorsById(UUID.randomUUID().toString());
            Assertions.fail(TestApplicationConstants.DATA_NOT_FOUND_EXCEPTION_NOT_THROWN_MESSAGE);
        } catch (DataNotFoundException dataNotFoundException) {
            ResponseEntity<DoctorResponse> doctorData = ResponseEntity.status(HttpStatus.NOT_FOUND).body(DoctorResponse.builder().success(false).errors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE)).build());
            assertEquals(HttpStatus.NOT_FOUND.value(), doctorData.getStatusCode().value());
            assertEquals(expectedResponse, doctorData.getBody());
            Mockito.verify(doctorValidation, Mockito.times(1)).isInValidUUID(Mockito.anyString());
            Mockito.verify(doctorRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        }
    }

    /**
     * Tests valid doctor name input and expects a successful response with a list of doctors and HTTP 200.
     */
    @Test
    void testRetrieveDoctorByName_withValidInput_returnsDoctorListResponse() {
        DoctorEntity doctorEntityResponse = testDataBuilder.doctorEntityBuilder();
        List<DoctorDto> expectedDoctorDtoList = testDataBuilder.doctorDtoListBuilder();
        List<DoctorEntity> expectedResponse = testDataBuilder.doctorEntityListBuilder();
        Mockito.when(doctorRepository.findByName(doctorEntityResponse.getFirstName())).thenReturn(expectedResponse);
        ResponseEntity<DoctorListResponse> actualResponse = doctorRetrievalService.retrieveDoctorByName(doctorEntityResponse.getFirstName());
        assertEquals(HttpStatus.OK.value(), actualResponse.getStatusCode().value());
        assertNotNull(actualResponse.getBody());
        assertTrue(actualResponse.getBody().isSuccess());
        assertEquals(expectedDoctorDtoList, actualResponse.getBody().getDoctors());
        Mockito.verify(doctorRepository, Mockito.times(1)).findByName(Mockito.anyString());
    }

    /**
     * Tests case where no doctor matches the given name and expects a 404 Not Found response with an error message.
     */
    @Test
    void testRetrieveDoctorByName_withNotMatchingDoctorName_returnsNotFoundErrorResponse() {
        DoctorEntity doctorEntityResponse = testDataBuilder.doctorEntityBuilder();
        Mockito.when(doctorRepository.findByName(doctorEntityResponse.getFirstName())).thenReturn(List.of());
        ResponseEntity<DoctorListResponse> doctorListResponse = doctorRetrievalService.retrieveDoctorByName(doctorEntityResponse.getFirstName());
        assertNotNull(doctorListResponse.getBody());
        assertFalse(doctorListResponse.getBody().isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, doctorListResponse.getStatusCode());
        assertEquals(1, doctorListResponse.getBody().getErrors().size());
        assertEquals(TestApplicationConstants.DOCTORS_NOT_FOUND, doctorListResponse.getBody().getErrors().get(0));
        Mockito.verify(doctorRepository, Mockito.times(1)).findByName(Mockito.anyString());
    }

    /**
     * Tests invalid (blank) doctor name input and expects a 400 Bad Request response with an error message.
     */
    @Test
    void testRetrieveDoctorByName_withInValidDoctorName_returnsBadRequestErrorResponse() {
        Mockito.when(doctorValidation.validateDoctorName(TestApplicationConstants.EMPTY_QUERY_STRING)).thenReturn(true);
        ResponseEntity<DoctorListResponse> doctorListResponse = doctorRetrievalService.retrieveDoctorByName(TestApplicationConstants.EMPTY_QUERY_STRING);
        assertNotNull(doctorListResponse.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, doctorListResponse.getStatusCode());
        assertEquals(1, doctorListResponse.getBody().getErrors().size());
        assertFalse(doctorListResponse.getBody().isSuccess());
        assertEquals(TestApplicationConstants.EMPTY_NAME_QUERY_PARAM, doctorListResponse.getBody().getErrors().get(0));
        Mockito.verify(doctorValidation, Mockito.times(1)).validateDoctorName(Mockito.anyString());
    }
// Test Cases for Get assigned doctors by patient id.

    /**
     * Test Method for testing the happy path of Get assigned doctors by patient id method of DoctorRetrievalService
     * HTTP Status code-200
     *
     * @throws InvalidUserInputException - if invalid user inputs are provided by the user
     */
    @Test
    void getDoctorsWithPatient_withValidInputs_returns200StatusAndValidDoctorResponse() throws Exception {
        AssignedDoctorsToPatientDto aggregationResponse = testDataBuilder.assignmentsToPatientDtoBuilder();
        DoctorPatientAssignmentResponse expectedResponse = testDataBuilder.assignmentResponseBuilder();
        Mockito.when(patientRepository.getAssignedDoctorsByPatientId(Mockito.any(UUID.class))).thenReturn(aggregationResponse);
        Mockito.when(patientRepository.existsById(Mockito.any(UUID.class))).thenReturn(true);
        ResponseEntity<DoctorPatientAssignmentResponse> getDoctorsWithPatient = doctorRetrievalService.getDoctorsWithPatient(UUID.randomUUID().toString());
        assertEquals(HttpStatus.OK.value(), getDoctorsWithPatient.getStatusCode().value());
        assertEquals(expectedResponse, getDoctorsWithPatient.getBody());
        Mockito.verify(patientRepository, Mockito.times(1)).getAssignedDoctorsByPatientId(Mockito.any(UUID.class));
        Mockito.verify(doctorValidation, Mockito.times(1)).isInValidUUID(Mockito.anyString());
    }

    /**
     * Method to test invalidUUID user input for getDoctorsWithPatient method of DoctorRetrieval service class
     * HTTP Status Code-400
     *
     * @throws Exception if invalid user provide invalid uuid
     */
    @Test
    void getDoctorsWithPatient_withInvalidUUID_returns400StatusAndInvalidDoctorResponse() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        Mockito.when(doctorValidation.isInValidUUID(Mockito.anyString())).thenReturn(true);
        try {
            doctorRetrievalService.getDoctorsWithPatient(UUID.randomUUID().toString());
            Assertions.fail(TestApplicationConstants.EXPECTED_INVALID_UUID_EXCEPTION);
        } catch (InvalidUuidException invalidUuidException) {
            ResponseEntity<DoctorResponse> doctorData = ResponseEntity.badRequest().body(DoctorResponse.builder().success(false).errors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE)).build());
            assertEquals(HttpStatus.BAD_REQUEST.value(), doctorData.getStatusCode().value());
            assertEquals(expectedResponse, doctorData.getBody());
            Mockito.verify(doctorValidation, Mockito.times(1)).isInValidUUID(Mockito.anyString());
            Mockito.verify(patientRepository, Mockito.never()).findById(Mockito.any(UUID.class));
        }
    }

    /**
     * Method to test empty UUID input from user for getDoctorsWithPatient method of DoctorRetrieval service class
     * HTTP Status Code-400
     *
     * @throws Exception if invalid user provide invalid uuid
     */
    @Test
    void getDoctorsWithPatient_withEmptyUUID_returns400StatusAndInvalidDoctorResponse() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        try {
            doctorRetrievalService.getDoctorsWithPatient(null);
            Assertions.fail(TestApplicationConstants.EXPECTED_EMPTY_UUID_EXCEPTION);
        } catch (EmptyUuidException emptyUuidException) {
            ResponseEntity<DoctorResponse> doctorData = ResponseEntity.badRequest().body(DoctorResponse.builder().success(false).errors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE)).build());
            assertEquals(HttpStatus.BAD_REQUEST.value(), doctorData.getStatusCode().value());
            assertEquals(expectedResponse, doctorData.getBody());
            Mockito.verify(doctorValidation, Mockito.never()).isInValidUUID(Mockito.anyString());
            Mockito.verify(patientRepository, Mockito.never()).findById(Mockito.any(UUID.class));
        }
    }

    /**
     * Method to test if no user exist for the give patient id
     * HTTP Status Code-404
     *
     * @throws Exception if no user is present in db
     */
    @Test
    void getDoctorsWithPatient_patientNotFoundError_returns404StatusAndInvalidDoctorResponse() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        Mockito.when(patientRepository.existsById(Mockito.any(UUID.class))).thenReturn(false);
        try {
            doctorRetrievalService.getDoctorsWithPatient(UUID.randomUUID().toString());
            Assertions.fail(TestApplicationConstants.EXPECTED_DATA_NOT_FOUND_EXCEPTION);
        } catch (DataNotFoundException dataNotFoundException) {
            ResponseEntity<DoctorResponse> doctorData = ResponseEntity.status(HttpStatus.NOT_FOUND).body(DoctorResponse.builder().success(false).errors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE)).build());
            assertEquals(HttpStatus.NOT_FOUND.value(), doctorData.getStatusCode().value());
            assertEquals(expectedResponse, doctorData.getBody());
            Mockito.verify(doctorValidation, Mockito.times(1)).isInValidUUID(Mockito.anyString());
            Mockito.verify(patientRepository, Mockito.never()).findById(Mockito.any(UUID.class));
        }
    }

    /**
     * Method to test if no doctor are assigned to the given patient
     * HTTP Status Code-404
     *
     * @throws Exception if no user is present in db
     */
    @Test
    void getDoctorsWithPatient_noDoctorAssignedError_returns404StatusAndInvalidDoctorResponse() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        Mockito.when(patientRepository.existsById(Mockito.any(UUID.class))).thenReturn(true);
        Mockito.when(patientRepository.getAssignedDoctorsByPatientId(Mockito.any(UUID.class))).thenReturn(null);
        try {
            doctorRetrievalService.getDoctorsWithPatient(UUID.randomUUID().toString());
            Assertions.fail(TestApplicationConstants.EXPECTED_DATA_NOT_FOUND_EXCEPTION);
        } catch (DataNotFoundException dataNotFoundException) {
            ResponseEntity<DoctorResponse> doctorData = ResponseEntity.status(HttpStatus.NOT_FOUND).body(DoctorResponse.builder().success(false).errors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE)).build());
            assertEquals(HttpStatus.NOT_FOUND.value(), doctorData.getStatusCode().value());
            assertEquals(expectedResponse, doctorData.getBody());
            Mockito.verify(doctorValidation, Mockito.times(1)).isInValidUUID(Mockito.anyString());
            Mockito.verify(patientRepository, Mockito.never()).findById(Mockito.any(UUID.class));
        }
    }
}
