package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.GlobalExceptionHandler;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientCreationService;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit test class for PatientCreationController
 */
@ExtendWith(MockitoExtension.class)
public class PatientCreationControllerTest {

    private MockMvc mockMvc;
    @Mock
    private PatientCreationService patientCreationService;
    @Mock
    private MessageUtil messageUtil;
    private TestDataBuilder testDataBuilder;
    private ObjectMapper objectMapper;

    /**
     * Initializes the TestDataBuilder before each test case
     * sets up MockMvc ith standaloneSetup
     * Registers the GlobalExceptionHandler to test exception handling
     * TestDataBuilder is used to provide test data for DTOs and Responses
     */
    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PatientCreationController(patientCreationService))
                .setControllerAdvice(new GlobalExceptionHandler(messageUtil))
                .build();
        testDataBuilder = new TestDataBuilder();
        objectMapper = new ObjectMapper();
    }

    /**
     * Test case for successful patient creation through the controller
     * Sends valid JSON request
     * Expects 200 OK response
     * Checks if the response body matches expected data
     */
    @Test
    public void testPatientCreationController_Success() throws Exception {
        PatientDto patient = testDataBuilder.patientDtoBuilder();
        PatientResponse expectedPatientResponse = testDataBuilder.patientResponseBuilder();
        Mockito.when(patientCreationService.createPatient(Mockito.any(PatientDto.class))).thenReturn(ResponseEntity.ok(expectedPatientResponse));
        mockMvc.perform(post(TestApplicationConstants.POST_PATIENTS_END_POINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPatientResponse)));
    }

    /**
     * Test case for negative scenario
     * Expects 400 bad request
     * verifies error message from GlobalExceptionHandler
     */
    @Test
    public void testPatientCreationController_Invalid() throws Exception {
        PatientDto patient = testDataBuilder.patientDtoBuilder();
        PatientResponse expectedPatientResponse = testDataBuilder.invalidPatientResponseBuilder();
        Mockito.when(patientCreationService.createPatient(Mockito.any(PatientDto.class))).thenThrow(new InvalidUserInputException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        mockMvc.perform(post(TestApplicationConstants.POST_PATIENTS_END_POINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedPatientResponse)));
    }
}
