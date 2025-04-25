package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.GlobalExceptionHandler;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorRetrievalService;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for DoctorRetrievalController using MockMvc and Mockito.
 */
@ExtendWith(MockitoExtension.class)
class DoctorRetrievalControllerTest {


    private MockMvc mockMvc;
    @Mock
    private DoctorRetrievalService doctorRetrievalService;
    @Mock
    private MessageUtil messageUtil;
    private ObjectMapper objectMapper;
    @InjectMocks
    private DoctorRetrievalController doctorRetrievalController;
    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DoctorRetrievalController(doctorRetrievalService))
                .setControllerAdvice(new GlobalExceptionHandler(messageUtil))
                .build();
        testDataBuilder = new TestDataBuilder();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_retrieveDoctorByName_withValidName_returnDoctorListResponse() throws Exception {
        ResponseEntity<DoctorListResponse> expectedResponse = testDataBuilder.buildDoctorListSuccessResponse(testDataBuilder.doctorDtoListBuilder());
        when(doctorRetrievalService.retrieveDoctorByName(ApplicationConstants.FIRST_NAME)).thenReturn(expectedResponse);
        ResultActions resultActions = mockMvc.perform(get(TestApplicationConstants.DOCTOR_ENDPOINT)
                        .param(TestApplicationConstants.QUERY_PARAM_NAME, TestApplicationConstants.FIRST_NAME)
                        .contentType(TestApplicationConstants.APPLICATION_JSON))
                .andExpect(status().isOk());
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        String expectedResponseString = objectMapper.writeValueAsString(expectedResponse.getBody());
        assertNotNull(actualResponse);
        assertEquals(expectedResponseString, actualResponse);
    }

    /**
     * Tests response when an invalid (blank) name is passed as input.
     */
    @Test
    void test_retrieveDoctorByName_withBlankName_returnsBadRequest() throws Exception {
        when(doctorRetrievalService.retrieveDoctorByName(TestApplicationConstants.EMPTY_QUERY_STRING)).thenReturn(testDataBuilder.buildDoctorListErrorResponse(HttpStatus.BAD_REQUEST));
        mockMvc.perform(get(TestApplicationConstants.DOCTOR_ENDPOINT)
                        .param(TestApplicationConstants.QUERY_PARAM_NAME, TestApplicationConstants.EMPTY_QUERY_STRING))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(TestApplicationConstants.JSON_KEY_SUCCESS).value(false))
                .andExpect(jsonPath(TestApplicationConstants.JSON_KEY_ERRORS).value(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
    }

    /**
     * Tests response when a valid name is passed but no doctor is found.
     */
    @Test
    void test_retrieveDoctorByName_withValidNameButNoMatch_returnsNotFound() throws Exception {
        when(doctorRetrievalService.retrieveDoctorByName(TestApplicationConstants.FIRST_NAME)).thenReturn(testDataBuilder.buildDoctorListErrorResponse(HttpStatus.NOT_FOUND));
        mockMvc.perform(get(TestApplicationConstants.DOCTOR_ENDPOINT)
                        .param(TestApplicationConstants.QUERY_PARAM_NAME, TestApplicationConstants.FIRST_NAME))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(TestApplicationConstants.JSON_KEY_SUCCESS).value(false))
                .andExpect(jsonPath(TestApplicationConstants.JSON_KEY_ERRORS).value(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
    }
}
