package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.GlobalExceptionHandler;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.DoctorPatientAssignmentResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorRetrievalService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

/**
 * Unit tests for DoctorRetrievalController using MockMvc and Mockito.
 */
@ExtendWith(MockitoExtension.class)
class DoctorRetrievalControllerTest {
    @Mock
    DoctorRetrievalService doctorRetrievalService;
    @Mock
    MessageUtil messageUtil;
    @InjectMocks
    DoctorRetrievalController doctorRetrievalController;
    private TestDataBuilder testDataBuilder;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(doctorRetrievalController)
                .setControllerAdvice(new GlobalExceptionHandler(messageUtil))
                .build();
        testDataBuilder = new TestDataBuilder();
        objectMapper = new ObjectMapper();
    }
// Test Cases for Get Doctors by id

    /**
     * Method to testing Happy Path of getDoctorById method of DoctorRetrievalController
     * HTTP Status Code-200
     *
     * @throws Exception - throws exception if user provide invalid inputs
     */
    @Test
    void getDoctorByID_withValidInput_returns200StatusAndValidDoctorResponse() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.doctorResponseBuilder();
        Mockito.when(doctorRetrievalService.getDoctorsById(Mockito.anyString())).thenReturn(ResponseEntity.ok().body(expectedResponse));
        ResultActions resultActions = mockMvc.perform(get(TestApplicationConstants.GET_DOCTOR_BY_ID, TestApplicationConstants.UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedResponse), actualResponse);
    }

    /**
     * Method to test invalid user input for getDoctorByID method of DoctorRetrieval Controller class
     * HTTP Status Code-400
     *
     * @throws Exception if invalid user provide invalid uuid
     */
    @Test
    void getDoctorByID_withInvalidUUID_returns400StatusAndInvalidDoctorResponse() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        Mockito.when(doctorRetrievalService.getDoctorsById(Mockito.anyString())).thenThrow(new InvalidUuidException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        ResultActions resultActions = mockMvc.perform(get(TestApplicationConstants.GET_DOCTOR_BY_ID, TestApplicationConstants.UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedResponse), actualResponse);
    }

    /**
     * Method to test if no user exist for the give doctor id
     * HTTP Status Code-404
     *
     * @throws Exception if no user is present in db
     */
    @Test
    void getDoctorByID_userNotFoundError_returns404StatusAndInvalidDoctorResponse() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        Mockito.when(doctorRetrievalService.getDoctorsById(Mockito.anyString())).thenThrow(new DataNotFoundException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        ResultActions resultActions = mockMvc.perform(get(TestApplicationConstants.GET_DOCTOR_BY_ID, TestApplicationConstants.UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedResponse), actualResponse);
    }

    /**
     * Method to test Internal server error for getDoctorByID method
     * HTTP Status Code-500
     *
     * @throws Exception if any unhandled exception occurs
     */
    @Test
    void getDoctorByID_returns500StatusAndInvalidDoctorResponse() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        expectedResponse.setErrors(List.of(null + TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        Mockito.when(doctorRetrievalService.getDoctorsById(Mockito.anyString())).thenThrow(new Exception(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        ResultActions resultActions = mockMvc.perform(get(TestApplicationConstants.GET_DOCTOR_BY_ID, TestApplicationConstants.UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedResponse), actualResponse);
    }

    @Test
    void test_retrieveDoctorByName_withValidName_returns200StatusAndDoctorListResponse() throws Exception {
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
    void test_retrieveDoctorByName_withBlankName_returns400StatusAndBadRequest() throws Exception {
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
    void test_retrieveDoctorByName_withValidNameButNoMatch_returns404StatusAndNotFound() throws Exception {
        when(doctorRetrievalService.retrieveDoctorByName(TestApplicationConstants.FIRST_NAME)).thenReturn(testDataBuilder.buildDoctorListErrorResponse(HttpStatus.NOT_FOUND));
        mockMvc.perform(get(TestApplicationConstants.DOCTOR_ENDPOINT)
                        .param(TestApplicationConstants.QUERY_PARAM_NAME, TestApplicationConstants.FIRST_NAME))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(TestApplicationConstants.JSON_KEY_SUCCESS).value(false))
                .andExpect(jsonPath(TestApplicationConstants.JSON_KEY_ERRORS).value(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
    }

    /**
     * Tests that retrieving a doctor by name returns 500 Internal Server Error when a server-side failure occurs.
     */
    @Test
    void test_retrieveDoctorByName_whenServerErrorOccurs_returns500StatusAndError() throws Exception {
        when(doctorRetrievalService.retrieveDoctorByName(TestApplicationConstants.FIRST_NAME)).thenReturn(testDataBuilder.buildDoctorListErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR));
        mockMvc.perform(get(TestApplicationConstants.DOCTOR_ENDPOINT)
                        .param(TestApplicationConstants.QUERY_PARAM_NAME, TestApplicationConstants.FIRST_NAME))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath(TestApplicationConstants.JSON_KEY_SUCCESS).value(false))
                .andExpect(jsonPath(TestApplicationConstants.JSON_KEY_ERRORS).value(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
    }
// Test Cases for Get assigned doctors by patient id.

    /**
     * Method to testing Happy Path of getAssignedDoctorByPatientId method of DoctorRetrievalController
     * HTTP Status Code-200
     *
     * @throws Exception - throws exception if user provide invalid inputs
     */
    @Test
    void getAssignedDoctorsList_validInputs_returns200StatusAndReturnsDoctorPatientAssignmentResponse() throws Exception {
        DoctorPatientAssignmentResponse expectedResponse = testDataBuilder.assignmentResponseBuilder();
        Mockito.when(doctorRetrievalService.getDoctorsWithPatient(Mockito.anyString())).thenReturn(ResponseEntity.ok().body(expectedResponse));
        ResultActions resultActions = mockMvc.perform(get(TestApplicationConstants.GET_DOCTOR_BY_PATIENT_ID).param(TestApplicationConstants.PATIENT_ID, expectedResponse.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedResponse), actualResponse);
    }

    /**
     * Method to test invalid user input for getAssignedDoctorByPatientId method of DoctorRetrieval Controller class
     * HTTP Status Code-400
     *
     * @throws Exception if invalid user provide invalid uuid
     */
    @Test
    void getAssignedDoctorsList_invalidUUID_returns400StatusAndInvalidDoctorResponse() throws Exception {
        String expectedResponse = objectMapper.writeValueAsString(testDataBuilder.invalidDoctorResponseBuilder());
        Mockito.when(doctorRetrievalService.getDoctorsWithPatient(Mockito.anyString())).thenThrow(new InvalidUuidException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        ResultActions resultActions = mockMvc.perform(get(TestApplicationConstants.GET_DOCTOR_BY_PATIENT_ID).param(TestApplicationConstants.PATIENT_ID, TestApplicationConstants.UUID))
                .andExpect(status().isBadRequest());
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    /**
     * Method to test if no user exist for the give patient id or no doctors are assigned to the patient
     * HTTP Status Code-404
     *
     * @throws Exception if no user is present in db
     */
    @Test
    void getAssignedDoctorsList_userNotFound_returns404StatusAndInvalidDoctorResponse() throws Exception {
        String expectedResponse = objectMapper.writeValueAsString(testDataBuilder.invalidDoctorResponseBuilder());
        Mockito.when(doctorRetrievalService.getDoctorsWithPatient(Mockito.anyString())).thenThrow(new DataNotFoundException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        ResultActions resultActions = mockMvc.perform(get(TestApplicationConstants.GET_DOCTOR_BY_PATIENT_ID).param(TestApplicationConstants.PATIENT_ID, TestApplicationConstants.UUID))
                .andExpect(status().isNotFound());
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    /**
     * Method to test Internal server error for getAssignedDoctorsList method
     * HTTP Status Code-500
     *
     * @throws Exception if any unhandled exception occurs
     */
    @Test
    void getAssignedDoctorsList_returns500StatusAndInvalidDoctorResponse() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        expectedResponse.setErrors(List.of(null + TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        Mockito.when(doctorRetrievalService.getDoctorsWithPatient(Mockito.anyString())).thenThrow(new Exception(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        ResultActions resultActions = mockMvc.perform(get(TestApplicationConstants.GET_DOCTOR_BY_PATIENT_ID).param(TestApplicationConstants.PATIENT_ID, TestApplicationConstants.UUID))
                .andExpect(status().isInternalServerError());
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedResponse), actualResponse);
    }
}
