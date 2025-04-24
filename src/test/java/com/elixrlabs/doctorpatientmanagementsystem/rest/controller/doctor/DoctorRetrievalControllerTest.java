package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorRetrievalService;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DoctorRetrievalController to verify doctor retrieval functionality using mock service.
 */
@ExtendWith(MockitoExtension.class)
class DoctorRetrievalControllerTest {

    @Mock
    DoctorRetrievalService doctorRetrievalService;
    @InjectMocks
    DoctorRetrievalController doctorRetrievalController;
    TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
    }

    /**
     * Method to testing Happy Path of getDoctorById method of DoctorRetrievalController
     * HTTP Status Code-200
     *
     * @throws Exception - throws exception if user provide invalid inputs
     *                   Tests retrieval of doctor by valid name and expects a successful response with doctor list.
     */
    @Test
    void getDoctorByID_validInput() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.doctorResponseBuilder();
        Mockito.when(doctorRetrievalService.getDoctorsById(Mockito.anyString())).thenReturn(ResponseEntity.ok().body(expectedResponse));
        ResponseEntity<DoctorResponse> doctorData = doctorRetrievalController.getDoctorByID(UUID.randomUUID().toString());
        assertEquals(HttpStatus.OK.value(), doctorData.getStatusCode().value());
        assertEquals(expectedResponse, doctorData.getBody());
        Mockito.verify(doctorRetrievalService, Mockito.times(1)).getDoctorsById(Mockito.anyString());
    }

    /**
     * Tests retrieval of doctor by valid name and expects a successful response with doctor list.
     */
    @Test
    void testRetrieveDoctorByName_withValidInput_returnsDoctorListResponse() {
        List<DoctorDto> expectedDoctorDtoListResponse = testDataBuilder.doctorDtoListBuilder();
        Mockito.when(doctorRetrievalService.retrieveDoctorByName(TestApplicationConstants.FIRST_NAME)).thenReturn(testDataBuilder.buildDoctorListSuccessResponse(expectedDoctorDtoListResponse));
        ResponseEntity<DoctorListResponse> actualResponse = doctorRetrievalController.getDoctorByName(TestApplicationConstants.FIRST_NAME);
        assertNotNull(actualResponse.getBody());
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertTrue(actualResponse.getBody().isSuccess());
        assertEquals(expectedDoctorDtoListResponse, actualResponse.getBody().getDoctors());
    }

    /**
     * Method to test invalid user input for getDoctorByID method of DoctorRetrieval Controller class
     * HTTP Status Code-400
     *
     * @throws Exception if invalid user provide invalid uuid
     *                   Tests retrieval with an invalid (blank) doctor name and expects a 400 Bad Request with error message.
     */
    @Test
    void getDoctorByID_invalidUUID() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        Mockito.when(doctorRetrievalService.getDoctorsById(Mockito.anyString())).thenThrow(new InvalidUuidException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        try {
            ResponseEntity<DoctorResponse> doctorData = doctorRetrievalController.getDoctorByID(TestApplicationConstants.INVALID_UUID);
            assertEquals(HttpStatus.BAD_REQUEST.value(), doctorData.getStatusCode().value());
            assertEquals(expectedResponse, doctorData.getBody());
            Mockito.verify(doctorRetrievalService, Mockito.never()).getDoctorsById(Mockito.anyString());
        } catch (InvalidUuidException invalidUuidException) {
            ResponseEntity.badRequest().body(expectedResponse);
        }
    }

    /**
     * Tests retrieval with an invalid (blank) doctor name and expects a 400 Bad Request with error message.
     */
    @Test
    void testRetrieveDoctorByName_withInValidDoctorName_returnsBadRequestErrorResponse() {
        Mockito.when(doctorRetrievalService.retrieveDoctorByName(TestApplicationConstants.BLANK_NAME)).thenReturn(testDataBuilder.buildDoctorListErrorResponse(HttpStatus.BAD_REQUEST));
        ResponseEntity<DoctorListResponse> actualResponse = doctorRetrievalController.getDoctorByName(TestApplicationConstants.BLANK_NAME);
        assertNotNull(actualResponse.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualResponse.getStatusCode());
        assertFalse(actualResponse.getBody().isSuccess());
        assertEquals(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE, actualResponse.getBody().getErrors().get(0));
    }

    /**
     * Method to test if no user exist for the give doctor id
     * HTTP Status Code-404
     *
     * @throws Exception if no user is present in db
     *                   Tests retrieval with a non-matching doctor name and expects a 404 Not Found with error message.
     */
    @Test
    void getDoctorByID_userNotFound() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        Mockito.when(doctorRetrievalService.getDoctorsById(Mockito.anyString())).thenThrow(new DataNotFoundException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        try {
            ResponseEntity<DoctorResponse> doctorData = doctorRetrievalController.getDoctorByID(TestApplicationConstants.INVALID_UUID);
            assertEquals(HttpStatus.NOT_FOUND.value(), doctorData.getStatusCode().value());
            assertEquals(expectedResponse, doctorData.getBody());
            Mockito.verify(doctorRetrievalService, Mockito.never()).getDoctorsById(Mockito.anyString());
        } catch (DataNotFoundException dataNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(expectedResponse);
        }
    }

    /**
     * Tests retrieval with a non-matching doctor name and expects a 404 Not Found with error message.
     */
    @Test
    void testRetrieveDoctorByName_withNotMatchingDoctorName_returnsNotFoundErrorResponse() {
        Mockito.when(doctorRetrievalService.retrieveDoctorByName(TestApplicationConstants.FIRST_NAME)).thenReturn(testDataBuilder.buildDoctorListErrorResponse(HttpStatus.NOT_FOUND));
        ResponseEntity<DoctorListResponse> actualResponse = doctorRetrievalController.getDoctorByName(TestApplicationConstants.FIRST_NAME);
        assertNotNull(actualResponse.getBody());
        assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode());
        assertFalse(actualResponse.getBody().isSuccess());
        assertEquals(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE, actualResponse.getBody().getErrors().get(0));
    }
}
