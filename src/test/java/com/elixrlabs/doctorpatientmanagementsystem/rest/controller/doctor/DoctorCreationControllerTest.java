package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.GlobalExceptionHandler;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorCreationService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class DoctorCreationControllerTest {
    @Mock
    DoctorCreationService doctorCreationService;
    @Mock
    MessageUtil messageUtil;
    @InjectMocks
    DoctorCreationController doctorCreationController;
    private TestDataBuilder testDataBuilder;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(doctorCreationController)
                .setControllerAdvice(new GlobalExceptionHandler(messageUtil))
                .build();
        testDataBuilder = new TestDataBuilder();
        objectMapper = new ObjectMapper();
    }

    /**
     * Method to testing Happy Path of createDoctor method
     * HTTP Status Code-200
     *
     * @throws Exception - throws exception if user provide invalid inputs
     */
    @Test
    public void testCreateDoctorController_validInputs() throws Exception {
        DoctorDto doctorDto = testDataBuilder.doctorDtoBuilder();
        DoctorResponse expectedResponse = testDataBuilder.doctorResponseBuilder();
        Mockito.when(doctorCreationService.createDoctor(Mockito.any(DoctorDto.class))).thenReturn(ResponseEntity.ok(expectedResponse));
        ResultActions resultActions = mockMvc.perform(post(TestApplicationConstants.DOCTORS_END_POINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorDto)))
                .andExpect(status().isOk());
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedResponse), actualResponse);
    }

    /**
     * Method to test invalid user input for createDoctor method of DoctorCreation Controller class
     * HTTP Status Code-400
     *
     * @throws Exception - invalidUserInputException is thrown by the service layer if validation fails
     */
    @Test
    void estCreateDoctorController_inValidInputs() throws Exception {
        DoctorDto doctorDto = testDataBuilder.doctorDtoBuilder();
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        Mockito.when(doctorCreationService.createDoctor(doctorDto)).thenThrow(new InvalidUserInputException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        ResultActions resultActions = mockMvc.perform(post(TestApplicationConstants.DOCTORS_END_POINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctorDto)))
                .andExpect(status().isBadRequest());
        String actualResponse = resultActions.andReturn().getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(expectedResponse), actualResponse);
    }
}
