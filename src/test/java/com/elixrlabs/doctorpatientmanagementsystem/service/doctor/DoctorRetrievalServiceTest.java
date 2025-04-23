package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;

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
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for DoctorRetrievalService class to validate doctor retrieval functionality.
 */
@ExtendWith(MockitoExtension.class)
class DoctorRetrievalServiceTest {

    @Mock
    DoctorValidation doctorValidation;
    @Mock
    DoctorRepository doctorRepository;
    @InjectMocks
    DoctorRetrievalService doctorRetrievalService;
    TestDataBuilder testDataBuilder = new TestDataBuilder();

    /**
     * Tests valid doctor name input and checks if the service returns correct doctor data with HTTP 200.
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
    }

    /**
     * Tests invalid (blank) doctor name input, expecting HTTP 400 response with appropriate error message.
     */
    @Test
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
     * Tests scenario when no doctors are found, expecting HTTP 404 with a "doctors not found" error message.
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
    }
}
