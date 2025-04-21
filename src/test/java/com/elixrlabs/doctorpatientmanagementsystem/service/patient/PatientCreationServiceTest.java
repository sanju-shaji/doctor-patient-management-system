package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUserInputException;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PatientCreationServiceTest {
    @Mock
    PatientRepository patientRepository;
    @Mock
    PatientValidation patientValidation;
    @InjectMocks
    PatientCreationService patientCreationService;
    @Test
    void testCreatePatientService_Success() throws InvalidUserInputException {
        PatientDto patient = PatientDto.builder()
                .firstName(ApplicationConstants.FIRST_NAME)
                .lastName(ApplicationConstants.LAST_NAME)
                .build();
        PatientModel patientModel = PatientModel.builder()
                .id(UUID.randomUUID())
                .firstName(ApplicationConstants.FIRST_NAME)
                .lastName(ApplicationConstants.LAST_NAME)
                .build();
        Mockito.when(patientRepository.save(Mockito.any(PatientModel.class))).thenReturn(patientModel);
    }
}
