package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.GlobalExceptionHandler;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.PatientValidationException;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatchPatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.patient.PatientModificationService;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
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
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
public class PatientModificationControllerTest {
    private TestDataBuilder testDataBuilder;
    private MockMvc mockMvc;
    @Mock
    private PatientModificationService patientModificationService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PatientModificationController(patientModificationService)).setControllerAdvice(new GlobalExceptionHandler(null)).build();
        objectMapper = new ObjectMapper();
        testDataBuilder = new TestDataBuilder();
    }

    /**
     * Test case for successful patient patch
     */
    @Test
    public void testPatchPatientById_Success() throws Exception {
        PatchPatientResponse patchPatientResponse = PatchPatientResponse.builder().id(UUID.fromString(TestApplicationConstants.UUID))
                .message(List.of(TestApplicationConstants.PATIENT_UPDATED_SUCCESSFULLY))
                .build();
        ResponseEntity<PatchPatientResponse> responseEntity = new ResponseEntity<>(patchPatientResponse, HttpStatus.OK);
        Mockito.when(patientModificationService.applyPatch(ArgumentMatchers.anyString(), Mockito.any(JsonPatch.class)))
                .thenReturn(responseEntity);
        String patchContent = TestApplicationConstants.PATCH_CONTENT;
        mockMvc.perform(MockMvcRequestBuilders.patch(TestApplicationConstants.PATCH_PATIENT_ENDPOINT, TestApplicationConstants.UUID)
                        .contentType(MediaType.valueOf(TestApplicationConstants.PATCH_CONTENT_TYPE))
                        .content(patchContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(TestApplicationConstants.UUID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message[0]").value(TestApplicationConstants.PATIENT_UPDATED_SUCCESSFULLY));
    }

    /**
     * Test case for patient not found
     * while patching(404)
     */
    @Test
    public void testPatchPatientById_PatientNotFound() throws Exception {
        PatientResponse expectedPatientResponse = testDataBuilder.invalidPatientResponseBuilder();
        Mockito.when(patientModificationService.applyPatch(ArgumentMatchers.anyString(), Mockito.any(JsonPatch.class)))
                .thenThrow(new DataNotFoundException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        String patchContent = TestApplicationConstants.PATCH_CONTENT;
        mockMvc.perform(MockMvcRequestBuilders.patch(TestApplicationConstants.PATCH_PATIENT_ENDPOINT, TestApplicationConstants.UUID)
                        .contentType(MediaType.valueOf(TestApplicationConstants.PATCH_CONTENT_TYPE))
                        .content(patchContent))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedPatientResponse)));
    }

    @Test
    public void testPatchPatientById_ValidationFailure() throws Exception {
        PatientResponse expectedPatientResponse = testDataBuilder.invalidPatientResponseBuilder();
        Mockito.when(patientModificationService.applyPatch(ArgumentMatchers.anyString(), Mockito.any(JsonPatch.class)))
                .thenThrow(new PatientValidationException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        String patchContent = TestApplicationConstants.PATCH_CONTENT;
        mockMvc.perform(MockMvcRequestBuilders.patch(TestApplicationConstants.PATCH_PATIENT_ENDPOINT, TestApplicationConstants.UUID)
                        .contentType(MediaType.valueOf(TestApplicationConstants.PATCH_CONTENT_TYPE))
                        .content(patchContent))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(expectedPatientResponse)));
    }
}