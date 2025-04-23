package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for DoctorRetrievalService class to validate doctor retrieval functionality.
 */
@ExtendWith(MockitoExtension.class)
class DoctorRetrievalServiceTest {

    @Mock
    DoctorRepository doctorRepository;
    @Mock
    DoctorValidation doctorValidation;
    @Mock
    MessageUtil messageUtil;
    DoctorRepository doctorRepository;
    @InjectMocks
    DoctorRetrievalService doctorRetrievalService;
    TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
    }
    TestDataBuilder testDataBuilder = new TestDataBuilder();

    /**
     * Test Method for testing the happy path
     * HTTP Status code-200
     *
     * @throws InvalidUserInputException - if invalid user inputs are provided by the user
     * Tests valid doctor name input and checks if the service returns correct doctor data with HTTP 200.
     */
    @Test
    void getDoctorsById_validInputs() throws Exception {
        DoctorEntity doctorEntity = testDataBuilder.doctorEntityBuilder();
        DoctorResponse expectedResponse = testDataBuilder.doctorResponseBuilder();
        assert doctorEntity != null;
        Mockito.when(doctorRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(doctorEntity));
        ResponseEntity<DoctorResponse> doctorData = doctorRetrievalService.getDoctorsById(doctorEntity.getId().toString());
        assertEquals(HttpStatus.OK.value(), doctorData.getStatusCode().value());
        assertEquals(expectedResponse, doctorData.getBody());
        Mockito.verify(doctorValidation, Mockito.times(1)).isInValidUUID(Mockito.anyString());
        Mockito.verify(doctorRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
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
    }

    /**
     * Method to test invalidUUID user input for getDoctorByID method of DoctorRetrieval service class
     * HTTP Status Code-400
     *
     * @throws Exception if invalid user provide invalid uuid
     * Tests invalid (blank) doctor name input, expecting HTTP 400 response with appropriate error message.
     */
    @Test
    void getDoctorsById_invalidUUID() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        Mockito.when(doctorValidation.isInValidUUID(Mockito.anyString())).thenReturn(true);
        try {
            ResponseEntity<DoctorResponse> doctorData = doctorRetrievalService.getDoctorsById(TestApplicationConstants.INVALID_UUID);
            assertEquals(HttpStatus.BAD_REQUEST.value(), doctorData.getStatusCode().value());
            assertEquals(expectedResponse, doctorData.getBody());
            Mockito.verify(doctorValidation, Mockito.times(1)).isInValidUUID(Mockito.anyString());
            Mockito.verify(doctorRepository, Mockito.never()).findById(Mockito.any(UUID.class));
        } catch (InvalidUuidException invalidUuidException) {
            ResponseEntity.badRequest().body(DoctorResponse.builder().success(false).errors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE)).build());
        }
    void testRetrieveDoctorByName_withInValidDoctorName_returnsBadRequestErrorResponse() {
        Mockito.when(doctorValidation.validateDoctorName(TestApplicationConstants.BLANK_NAME)).thenReturn(true);
        ResponseEntity<DoctorListResponse> doctorListResponse = doctorRetrievalService.retrieveDoctorByName(TestApplicationConstants.BLANK_NAME);
        assertNotNull(doctorListResponse.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, doctorListResponse.getStatusCode());
        assertEquals(1, doctorListResponse.getBody().getErrors().size());
        assertFalse(doctorListResponse.getBody().isSuccess());
        assertEquals(TestApplicationConstants.EMPTY_NAME_QUERY_PARAM, doctorListResponse.getBody().getErrors().get(0));
    }

    /**
     * Method to test if no user exist for the give doctor id
     * HTTP Status Code-404
     *
     * @throws Exception if no user is present in db
     * Tests scenario when no doctors are found, expecting HTTP 404 with a "doctors not found" error message.
     */
    @Test
    void getDoctorsById_userNotFound() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        Mockito.when(doctorValidation.isInValidUUID(Mockito.anyString())).thenReturn(false);
        Mockito.when(doctorRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());
        try {
            ResponseEntity<DoctorResponse> doctorData = doctorRetrievalService.getDoctorsById(UUID.randomUUID().toString());
            assertEquals(HttpStatus.NOT_FOUND.value(), doctorData.getStatusCode().value());
            assertEquals(expectedResponse, doctorData.getBody());
            Mockito.verify(doctorValidation, Mockito.times(1)).isInValidUUID(Mockito.anyString());
            Mockito.verify(doctorRepository, Mockito.never()).findById(Mockito.any(UUID.class));
        } catch (DataNotFoundException dataNotFoundException) {
            ResponseEntity.badRequest().body(DoctorResponse.builder().success(false).errors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE)).build());
        }
    void testRetrieveDoctorByName_withNotMatchingDoctorName_returnsNotFoundErrorResponse() {
        DoctorEntity doctorEntityResponse = testDataBuilder.doctorEntityBuilder();
        Mockito.when(doctorRepository.findByName(doctorEntityResponse.getFirstName())).thenReturn(List.of());
        ResponseEntity<DoctorListResponse> doctorListResponse = doctorRetrievalService.retrieveDoctorByName(doctorEntityResponse.getFirstName());
        assertNotNull(doctorListResponse.getBody());
        assertFalse(doctorListResponse.getBody().isSuccess());
        assertEquals(HttpStatus.NOT_FOUND, doctorListResponse.getStatusCode());
        assertEquals(1, doctorListResponse.getBody().getErrors().size());
        assertEquals(TestApplicationConstants.DOCTORS_NOT_FOUND, doctorListResponse.getBody().getErrors().get(0));
    }
}
