package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.GlobalExceptionHandler;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.PatientAlreadyAssignedException;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientDeletionService;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class PatientDeletionControllerTest {

    private MockMvc mockMvc;
    @Mock
    private PatientDeletionService patientDeletionService;

    @Mock
    private MessageUtil messageUtil;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PatientDeletionController(patientDeletionService)).setControllerAdvice(new GlobalExceptionHandler(messageUtil)).build();
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
        Mockito.when(patientDeletionService.deletePatientById(anyString())).thenReturn(responseEntity);
        mockMvc.perform(delete("/patient/{patientId}", TestApplicationConstants.UUID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.messages[0]").value(TestApplicationConstants.PATIENT_DELETED_SUCCESSFULLY));
    }

    /**
     * Test case for patient not found scenario
     */
    @Test
    public void testDeletePatientController_PatientNotFound() throws Exception {
        Mockito.when(patientDeletionService.deletePatientById(anyString()))
                .thenThrow(new DataNotFoundException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));

        mockMvc.perform(delete("/patient/{patientId}", TestApplicationConstants.UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors").value(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
    }

    /**
     * Test case for patient already assigned scenario
     */
    @Test
    public void testDeletePatientController_PatientAlreadyAssigned() throws Exception {
        Mockito.when(patientDeletionService.deletePatientById(anyString()))
                .thenThrow(new PatientAlreadyAssignedException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));

        mockMvc.perform(delete("/patient/{patientId}", TestApplicationConstants.UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors").value(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
    }
}
