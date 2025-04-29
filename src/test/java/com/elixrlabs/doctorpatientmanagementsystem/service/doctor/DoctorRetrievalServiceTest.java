package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
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

@ExtendWith(MockitoExtension.class)
class DoctorRetrievalServiceTest {
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

    /**
     * Test Method for testing the happy path
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
            Assertions.fail(TestApplicationConstants.EXPECTED_DATA_NOT_FOUND_EXCEPTION);
        } catch (DataNotFoundException dataNotFoundException) {
            ResponseEntity<DoctorResponse> doctorData = ResponseEntity.status(HttpStatus.NOT_FOUND).body(DoctorResponse.builder().success(false).errors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE)).build());
            assertEquals(HttpStatus.NOT_FOUND.value(), doctorData.getStatusCode().value());
            assertEquals(expectedResponse, doctorData.getBody());
            Mockito.verify(doctorValidation, Mockito.times(1)).isInValidUUID(Mockito.anyString());
            Mockito.verify(doctorRepository, Mockito.times(1)).findById(Mockito.any(UUID.class));
        }
    }
}
