package com.elixrlabs.doctorpatientmanagementsystem.rest.controller.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.service.doctor.DoctorCreationService;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class DoctorCreationControllerTest {
    @Mock
    DoctorCreationService doctorCreationService;
    @InjectMocks
    DoctorCreationController doctorCreationController;
    TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
    }

    /**
     * Method to testing Happy Path of createDoctor method
     * HTTP Status Code-200
     *
     * @throws Exception - throws exception if user provide invalid inputs
     */
    @Test
    void testCreateDoctorController_validInputs() throws Exception {
        DoctorDto doctorDto = testDataBuilder.doctorDtoBuilder();
        DoctorResponse doctorResponse = testDataBuilder.doctorResponseBuilder();
        Mockito.when(doctorCreationService.createDoctor(Mockito.any(DoctorDto.class))).thenReturn(ResponseEntity.ok().body(doctorResponse));
        ResponseEntity<DoctorResponse> doctorCreationResponse = doctorCreationController.createDoctor(doctorDto);
        assertNotNull(doctorCreationResponse.getBody());
        assertEquals(HttpStatus.OK.value(), doctorCreationResponse.getStatusCode().value());
        assertTrue(doctorCreationResponse.getBody().isSuccess());
        assertEquals(doctorDto.getFirstName(), doctorCreationResponse.getBody().getFirstName());
        assertEquals(doctorDto.getLastName(), doctorCreationResponse.getBody().getLastName());
        assertEquals(doctorDto.getDepartment(), doctorCreationResponse.getBody().getDepartment());
        Mockito.verify(doctorCreationService, Mockito.times(1)).createDoctor(doctorDto);
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
        Mockito.when(doctorCreationService.createDoctor(doctorDto)).thenThrow(new InvalidUserInputException(ApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        try {
            ResponseEntity<DoctorResponse> doctorCreationResponse = doctorCreationController.createDoctor(doctorDto);
            assertNotNull(doctorCreationResponse.getBody());
            assertEquals(HttpStatus.BAD_REQUEST.value(), doctorCreationResponse.getStatusCode().value());
            assertFalse(doctorCreationResponse.getBody().isSuccess());
            assertEquals(ApplicationConstants.MOCK_EXCEPTION_MESSAGE, doctorCreationResponse.getBody().getErrors().get(0));
            Mockito.verify(doctorCreationService, Mockito.times(1)).createDoctor(doctorDto);
        } catch (InvalidUserInputException invalidUserInputException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DoctorResponse.builder().success(false).errors(List.of(ApplicationConstants.MOCK_EXCEPTION_MESSAGE)));
        }
    }
}
