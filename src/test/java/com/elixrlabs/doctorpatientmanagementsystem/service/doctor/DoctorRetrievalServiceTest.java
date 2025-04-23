package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
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


    TestDataBuilder testDataBuilder;
    DoctorEntity doctorEntityResponse;
    List<DoctorEntity> doctorEntityListResponse;
    TestApplicationConstants testApplicationConstants = new TestApplicationConstants();

    /**
     * Sets up necessary test data before each test case.
     */
    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
        doctorEntityResponse = testDataBuilder.doctorEntityBuilder();
        doctorEntityListResponse = testDataBuilder.doctorEntityListBuilder();
    }

    /**
     * Tests valid doctor name input and checks if the service returns correct doctor data with HTTP 200.
     */
    @Test
    void testDoctorRetrievalService_validInputs() {
        Mockito.when(doctorRepository.findByName(doctorEntityResponse.getFirstName())).thenReturn(doctorEntityListResponse);
        ResponseEntity<DoctorListResponse> doctorsData = doctorRetrievalService.retrieveDoctorByName(doctorEntityResponse.getFirstName());

        assertNotNull(doctorsData.getBody());
        assertEquals(doctorEntityResponse.getFirstName(), doctorsData.getBody().getDoctors().get(0).getFirstName());
        assertEquals(doctorEntityResponse.getLastName(), doctorsData.getBody().getDoctors().get(0).getLastName());
        assertEquals(doctorEntityResponse.getDepartment(), doctorsData.getBody().getDoctors().get(0).getDepartment());
        assertEquals(HttpStatus.OK.value(), doctorsData.getStatusCode().value());
    }

    /**
     * Tests invalid (blank) doctor name input, expecting HTTP 400 response with appropriate error message.
     */
    @Test
    void testDoctorRetrievalService_inValidDoctorName() {
        Mockito.when(doctorValidation.validateDoctorName(testApplicationConstants.BLANK_NAME)).thenReturn(true);
        ResponseEntity<DoctorListResponse> doctorsData = doctorRetrievalService.retrieveDoctorByName(testApplicationConstants.BLANK_NAME);
        assertEquals(HttpStatus.BAD_REQUEST,doctorsData.getStatusCode());
        assertEquals(1,doctorsData.getBody().getErrors().size());
        assertFalse(doctorsData.getBody().isSuccess());
        assertEquals(testApplicationConstants.EMPTY_NAME_QUERY_PARAM,doctorsData.getBody().getErrors().get(0));
    }

    /**
     * Tests scenario when no doctors are found, expecting HTTP 404 with a "doctors not found" error message.
     */
    @Test
    void  testDoctorRetrievalService_emptyDoctorsData(){
        Mockito.when(doctorRepository.findByName(doctorEntityResponse.getFirstName())).thenReturn(List.of());
        ResponseEntity<DoctorListResponse> doctorsData = doctorRetrievalService.retrieveDoctorByName(doctorEntityResponse.getFirstName());
        assertEquals(HttpStatus.NOT_FOUND,doctorsData.getStatusCode());
        assertEquals(1,doctorsData.getBody().getErrors().size());
        assertFalse(doctorsData.getBody().isSuccess());
        assertEquals(doctorsData.getBody().getErrors().get(0),testApplicationConstants.DOCTORS_NOT_FOUND);
    }
}
