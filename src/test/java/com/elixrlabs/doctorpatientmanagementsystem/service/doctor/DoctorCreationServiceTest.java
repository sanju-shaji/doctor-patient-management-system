package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test class for testing the DoctorCreation service Class
 */
@ExtendWith(MockitoExtension.class)
class DoctorCreationServiceTest {
    @Mock
    DoctorRepository doctorRepository;
    @Mock
    DoctorValidation doctorValidation;
    @InjectMocks
    DoctorCreationService doctorCreationService;
    TestDataBuilder testDataBuilder;

    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
    }

    /**
     * Test Method for testing the happy path
     * HTTP Status code-200
     *
     * @throws InvalidUserInputException - if invalid user inputs are provided by the user
     */
    @Test
    void testCreateDoctorService_validInputs() throws InvalidUserInputException {
        DoctorDto doctorDto = testDataBuilder.doctorDtoBuilder();
        DoctorEntity doctorEntity = testDataBuilder.doctorEntityBuilder();
        Mockito.when(doctorRepository.save(Mockito.any(DoctorEntity.class))).thenReturn(doctorEntity);
        ResponseEntity<DoctorResponse> doctorCreationResponse = doctorCreationService.createDoctor(doctorDto);
        assertNotNull(doctorCreationResponse);
        assertEquals(HttpStatus.OK.value(), doctorCreationResponse.getStatusCode().value());
        assertNotNull(doctorCreationResponse.getBody());
        assertTrue(doctorCreationResponse.getBody().isSuccess());
        assertEquals(doctorEntity.getFirstName(), doctorCreationResponse.getBody().getFirstName());
        assertEquals(doctorEntity.getLastName(), doctorCreationResponse.getBody().getLastName());
        assertEquals(doctorEntity.getDepartment(), doctorCreationResponse.getBody().getDepartment());
        Mockito.verify(doctorValidation, Mockito.times(1)).validateDoctorDetails(doctorDto);
        ArgumentCaptor<DoctorEntity> captor = ArgumentCaptor.forClass(DoctorEntity.class);
        Mockito.verify(doctorRepository, Mockito.times(1)).save(captor.capture());
    }

    /**
     * Test method for testing the validateDoctorDetails method of Doctor Validation
     * HTTP Status code-400
     *
     * @throws InvalidUserInputException - if invalid user inputs are provided by the user
     */
    @Test
    void testCreateDoctorService_invalidInputs() throws InvalidUserInputException {
        DoctorDto doctorDto = testDataBuilder.doctorDtoBuilder();
        doctorDto.setFirstName(null);
        Mockito.doThrow(new InvalidUserInputException(ApplicationConstants.MOCK_EXCEPTION_MESSAGE)).when(doctorValidation).validateDoctorDetails(doctorDto);
        assertThrows(InvalidUserInputException.class, () -> {
            ResponseEntity<DoctorResponse> doctorCreationResponse = doctorCreationService.createDoctor(doctorDto);
            assertNotNull(doctorCreationResponse);
            assertEquals(HttpStatus.BAD_REQUEST.value(), doctorCreationResponse.getStatusCode().value());
            assertEquals(ApplicationConstants.MOCK_EXCEPTION_MESSAGE, doctorCreationResponse.getBody().getErrors().get(0));
            assertFalse(doctorCreationResponse.getBody().isSuccess());
        });
        Mockito.verify(doctorValidation, Mockito.times(1)).validateDoctorDetails(doctorDto);
        Mockito.verify(doctorRepository, Mockito.never()).save(Mockito.any(DoctorEntity.class));
    }
}
