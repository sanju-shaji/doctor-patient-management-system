package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit test class for PatientCreationService
 */
@ExtendWith(MockitoExtension.class)
public class PatientCreationServiceTest {
    @Mock
    PatientRepository patientRepository;
    @Mock
    PatientValidation patientValidation;
    @InjectMocks
    PatientCreationService patientCreationService;
    TestDataBuilder testDataBuilder;

    /**
     * Initializes the TestDataBuilder before each test execution
     */
    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
    }

    /**
     * Test case for successful PatientCreationService logic
     */
    @Test
    void testPatientCreationService_Success() throws Exception {
        PatientDto patient = testDataBuilder.patientDtoBuilder();
        PatientModel patientModel = testDataBuilder.patientModelBuilder();
        PatientResponse expectedPatientResponse = testDataBuilder.patientResponseBuilder();
        Mockito.when(patientRepository.save(Mockito.any(PatientModel.class))).thenReturn(patientModel);
        ResponseEntity<PatientResponse> patientCreationResponse = patientCreationService.createPatient(patient);
        Assertions.assertNotNull(patientCreationResponse);
        Assertions.assertEquals(HttpStatus.OK, patientCreationResponse.getStatusCode());
        Assertions.assertNotNull(patientCreationResponse.getBody());
        Assertions.assertTrue(patientCreationResponse.getBody().isSuccess());
        Assertions.assertEquals(expectedPatientResponse, patientCreationResponse.getBody());
        Mockito.verify(patientValidation, Mockito.times(1)).validatePatient(patient);
        ArgumentCaptor<PatientModel> captor = ArgumentCaptor.forClass(PatientModel.class);
        Mockito.verify(patientRepository, Mockito.times(1)).save(captor.capture());
    }

    /**
     * Test case for invalid input during patient creation
     */
    @Test
    void testPatientCreationService_invalidInputs() throws InvalidUserInputException {
        PatientDto patient = testDataBuilder.patientDtoBuilder();
        patient.setFirstName(TestApplicationConstants.BLANK_FIRST_NAME);
        Mockito.doThrow(new InvalidUserInputException(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE)).when(patientValidation).validatePatient(patient);
        Assertions.assertThrows(InvalidUserInputException.class, () -> {
            ResponseEntity<PatientResponse> patientCreationResponse = patientCreationService.createPatient(patient);
            Assertions.assertNotNull(patientCreationResponse);
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, patientCreationResponse.getStatusCode());
            Assertions.assertEquals(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE, patientCreationResponse.getBody().getErrors().get(0));
            Assertions.assertFalse(patientCreationResponse.getBody().isSuccess());
        });
        Mockito.verify(patientValidation, Mockito.times(1)).validatePatient(patient);
        Mockito.verify(patientRepository, Mockito.never()).save(Mockito.any(PatientModel.class));
    }
}
