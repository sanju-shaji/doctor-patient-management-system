package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorRetrievalService;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DoctorRetrievalController to verify doctor retrieval functionality using mock service.
 */
@ExtendWith(MockitoExtension.class)
class DoctorRetrievalControllerTest {
    TestDataBuilder testDataBuilder = new TestDataBuilder();

    @Mock
    DoctorRetrievalService doctorRetrievalService;
    @InjectMocks
    DoctorRetrievalController doctorRetrievalController;

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