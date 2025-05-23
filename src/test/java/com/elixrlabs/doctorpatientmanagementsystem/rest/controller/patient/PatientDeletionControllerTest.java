package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.GlobalExceptionHandler;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.PatientAlreadyAssignedException;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientDeletionService;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class PatientDeletionControllerTest {
    private TestDataBuilder testDataBuilder;

    private MockMvc mockMvc;
    @Mock
    private PatientDeletionService patientDeletionService;
    private ObjectMapper objectMapper;

    @Mock
    private MessageUtil messageUtil;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PatientDeletionController(patientDeletionService)).setControllerAdvice(new GlobalExceptionHandler(messageUtil)).build();
        testDataBuilder = new TestDataBuilder();
        objectMapper = new ObjectMapper();
    }

    /**
     * Method to testing Happy Path of createDoctor method
     * HTTP Status Code-200
     */
    @Test
    public void testDeletePatientById_Success() throws Exception {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(true);
        baseResponse.setMessages(List.of(TestApplicationConstants.PATIENT_DELETED_SUCCESSFULLY));
        ResponseEntity<BaseResponse> responseEntity = new ResponseEntity<>(baseResponse, HttpStatus.OK);
        Mockito.when(patientDeletionService.deletePatientById(ArgumentMatchers.anyString())).thenReturn(responseEntity);
        mockMvc.perform(MockMvcRequestBuilders.delete(TestApplicationConstants.PATIENT_ENDPOINT, TestApplicationConstants.UUID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(TestApplicationConstants.JSON_KEY_SUCCESS).value(true))
                .andExpect(MockMvcResultMatchers.jsonPath(TestApplicationConstants.JSON_PATH_MESSAGES_FIRST).value(TestApplicationConstants.PATIENT_DELETED_SUCCESSFULLY));
    }

    /**
     * Test case for patient not found scenario
     */
    @Test
    public void testDeletePatientController_PatientNotFound() throws Exception {
        PatientResponse expectedPatientResponse = testDataBuilder.invalidPatientResponseBuilder();
        Mockito.when(patientDeletionService.deletePatientById(ArgumentMatchers.anyString()))
                .thenThrow(new DataNotFoundException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));

        mockMvc.perform(MockMvcRequestBuilders.delete(TestApplicationConstants.PATIENT_ENDPOINT, TestApplicationConstants.UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedPatientResponse)));
    }

    /**
     * Test case for patient already assigned scenario
     */
    @Test
    public void testDeletePatientController_PatientAlreadyAssigned() throws Exception {
        PatientResponse expectedPatientResponse = testDataBuilder.invalidPatientResponseBuilder();
        Mockito.when(patientDeletionService.deletePatientById(ArgumentMatchers.anyString()))
                .thenThrow(new PatientAlreadyAssignedException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));

        mockMvc.perform(MockMvcRequestBuilders.delete(TestApplicationConstants.PATIENT_ENDPOINT, TestApplicationConstants.UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedPatientResponse)));
    }
}
