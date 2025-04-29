package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.GlobalExceptionHandler;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorDeletionService;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for DoctorDeletionController to verify delete doctor operations.
 */
@ExtendWith(MockitoExtension.class)
class DoctorDeletionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DoctorDeletionService doctorDeletionService;

    @Mock
    private MessageUtil messageUtil;

    private TestDataBuilder testDataBuilder;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DoctorDeletionController(doctorDeletionService))
                .setControllerAdvice(new GlobalExceptionHandler(messageUtil))
                .build();
        testDataBuilder = new TestDataBuilder();
    }

    /**
     * Test deleteDoctorById with valid UUID and verify success response.
     */
    @Test
    void test_deleteDoctorById_withValidId_return200StatusAndSuccessResponse() throws Exception {
        ResponseEntity<BaseResponse> successResponse =
                testDataBuilder.buildSuccessDeleteResponse(Collections.singletonList(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        when(doctorDeletionService.deleteDoctorById(TestApplicationConstants.UUID))
                .thenReturn(successResponse);
        mockMvc.perform(delete(TestApplicationConstants.DOCTOR_DELETE_ENDPOINT, TestApplicationConstants.UUID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(TestApplicationConstants.JSON_KEY_SUCCESS).value(true))
                .andExpect(jsonPath(TestApplicationConstants.JSON_PATH_MESSAGES_FIRST).value(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
    }

    /**
     * Test deleteDoctorById with empty ID and verify failure response.
     */
    @Test
    void test_deleteDoctorById_withEmptyId_returns400StatusAndFailureResponse() throws Exception {
        ResponseEntity<BaseResponse> failureResponse =
                testDataBuilder.buildFailureDeleteResponse(Collections.singletonList(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE), HttpStatus.BAD_REQUEST);
        when(doctorDeletionService.deleteDoctorById(TestApplicationConstants.EMPTY_QUERY_STRING))
                .thenReturn(failureResponse);
        mockMvc.perform(delete(TestApplicationConstants.DOCTOR_DELETE_ENDPOINT, TestApplicationConstants.EMPTY_QUERY_STRING))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(TestApplicationConstants.JSON_KEY_SUCCESS).value(false))
                .andExpect(jsonPath(TestApplicationConstants.JSON_PATH_ERRORS_FIRST).value(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
    }

    /**
     * Test deleteDoctorById with non-existing UUID and verify not found failure response.
     */
    @Test
    void test_retrieveDoctorByName_withBlankName_returns404StatusAndBadRequest() throws Exception {
        ResponseEntity<BaseResponse> failureResponse =
                testDataBuilder.buildFailureDeleteResponse(Collections.singletonList(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE), HttpStatus.NOT_FOUND);
        when(doctorDeletionService.deleteDoctorById(TestApplicationConstants.UUID))
                .thenReturn(failureResponse);
        mockMvc.perform(delete(TestApplicationConstants.DOCTOR_DELETE_ENDPOINT, TestApplicationConstants.UUID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(TestApplicationConstants.JSON_KEY_SUCCESS).value(false))
                .andExpect(jsonPath(TestApplicationConstants.JSON_PATH_ERRORS_FIRST).value(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
    }

    /**
     * Tests that delete a doctor by doctorId returns 500 Internal Server Error when a server-side failure occurs.
     */
    @Test
    void test_retrieveDoctorByName_whenServerErrorOccurs_returns500StatusAndError() throws Exception {
        ResponseEntity<BaseResponse> failureResponse =
                testDataBuilder.buildFailureDeleteResponse(Collections.singletonList(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
        when(doctorDeletionService.deleteDoctorById(TestApplicationConstants.UUID))
                .thenReturn(failureResponse);
        mockMvc.perform(delete(TestApplicationConstants.DOCTOR_DELETE_ENDPOINT, TestApplicationConstants.UUID))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath(TestApplicationConstants.JSON_KEY_SUCCESS).value(false))
                .andExpect(jsonPath(TestApplicationConstants.JSON_PATH_ERRORS_FIRST).value(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
    }
}
