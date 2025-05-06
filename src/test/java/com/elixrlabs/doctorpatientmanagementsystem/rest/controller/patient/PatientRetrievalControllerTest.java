package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.GlobalExceptionHandler;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientRetrievalService;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for PatientRetrievalController
 * It uses Mockito to mock dependencies and Spring's MockMvc for simulating Http Requests
 */
@ExtendWith(MockitoExtension.class)
class PatientRetrievalControllerTest {
    @Mock
    private PatientRetrievalService patientRetrievalService;
    @Mock
    private MessageUtil messageUtil;
    @InjectMocks
    private PatientRetrievalController patientRetrievalController;
    private TestDataBuilder testDataBuilder;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PatientRetrievalController(patientRetrievalService))
                .setControllerAdvice(new GlobalExceptionHandler(messageUtil))
                .build();
        testDataBuilder = new TestDataBuilder();
        objectMapper = new ObjectMapper();
    }

    /**
     * Test case for successfully retrieving patient data by valid UUID.
     * verifies that the controller returns Http 200 response OK and the expected response body.
     */
    @Test
    void getPatientById_Success() throws Exception {
        PatientResponse expectedPatientResponse = testDataBuilder.getPatientByIdResponseBuilder();
        Mockito.when(patientRetrievalService.getPatientById(Mockito.anyString())).thenReturn(ResponseEntity.ok().body(expectedPatientResponse));
        ResultActions resultActions = mockMvc.perform(get(TestApplicationConstants.GET_PATIENT_BY_ID_END_POINT, TestApplicationConstants.UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedPatientResponse), actualResponse);
    }

    /**
     * Test case for retrieving patient data using an invalid UUID
     * Verifies that the controller, returns Http 400 response Bad Request and the appropriate error message
     */
    @Test
    void getPatientByID_invalidUUID() throws Exception {
        PatientResponse expectedPatientResponse = testDataBuilder.invalidPatientResponseBuilder();
        Mockito.when(patientRetrievalService.getPatientById(Mockito.anyString())).thenThrow(new InvalidUuidException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        ResultActions resultActions = mockMvc.perform(get(TestApplicationConstants.GET_PATIENT_BY_ID_END_POINT, TestApplicationConstants.UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedPatientResponse), actualResponse);
    }

    /**
     * Test case for retrieving patient data when the patientId is valid but no corresponding patient is found in the system
     * Verifies that the controller returns Http 404 response Not Found and the appropriate error message
     */
    @Test
    void getPatientById_patientNotFound() throws Exception {
        PatientResponse expectedPatientResponse = testDataBuilder.invalidPatientResponseBuilder();
        Mockito.when(patientRetrievalService.getPatientById(Mockito.anyString())).thenThrow(new DataNotFoundException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        ResultActions resultActions = mockMvc.perform(get(TestApplicationConstants.GET_PATIENT_BY_ID_END_POINT, TestApplicationConstants.UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedPatientResponse), actualResponse);
    }

    /**
     * Method to test Internal server error for getPatientByID method
     *
     * @throws Exception if any unhandled exception occurs
     */
    @Test
    void getPatientByID_internalServerError() throws Exception {
        PatientResponse expectedPatientResponse = testDataBuilder.invalidPatientResponseBuilder();
        expectedPatientResponse.setErrors(List.of(null + TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        Mockito.when(patientRetrievalService.getPatientById(Mockito.anyString())).thenThrow(new Exception(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        ResultActions resultActions = mockMvc.perform(get(TestApplicationConstants.GET_PATIENT_BY_ID_END_POINT, TestApplicationConstants.UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedPatientResponse), actualResponse);
    }
}
