package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.PatientAlreadyAssignedException;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctorpatientassignment.DoctorPatientAssignmentModel;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment.DoctorPatientAssignmentRepository;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Test class for testing the PatientDeletion service Class
 */
@ExtendWith(MockitoExtension.class)
class PatientDeletionServiceTest {
    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorPatientAssignmentRepository doctorPatientAssignmentRepository;

    @Mock
    private PatientValidation patientValidation;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private PatientDeletionService patientDeletionService;

    /**
     * Test Method for testing the happy path
     * HTTP Status code-200
     */
    @Test
    void testDeletePatientById_Success() throws Exception {
        String patientId = UUID.randomUUID().toString();
        PatientModel patient = new PatientModel();
        patient.setId(UUID.fromString(patientId));
        when(patientRepository.findById(UUID.fromString(patientId))).thenReturn(Optional.of(patient));
        when(doctorPatientAssignmentRepository.findByPatientId(UUID.fromString(patientId))).thenReturn(Collections.emptyList());
        when(messageUtil.getMessage(MessageKeyEnum.PATIENT_DELETED_SUCCESSFULLY.getKey())).thenReturn(TestApplicationConstants.PATIENT_DELETED_SUCCESSFULLY);
        ResponseEntity<BaseResponse> response = patientDeletionService.deletePatientById(patientId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getMessages().contains(TestApplicationConstants.PATIENT_DELETED_SUCCESSFULLY));
        assertNull(response.getBody().getData());
        verify(patientRepository, times(1)).deleteById(UUID.fromString(patientId));
    }

    /**
     * Test method for testing the validateDoctorDetails method of Doctor Validation
     * HTTP Status code-404
     */
    @Test
    void testDeletePatientById_PatientNotFound() throws Exception {
        String patientId = UUID.randomUUID().toString();
        String expectedMessage = new StringBuilder().append(TestApplicationConstants.NO_PATIENT_FOUND_WITH_ID).append(patientId).toString();
        doNothing().when(patientValidation).validatePatientId(anyString());
        when(patientRepository.findById(UUID.fromString(patientId))).thenReturn(Optional.empty());
        when(messageUtil.getMessage(eq(MessageKeyEnum.NO_PATIENT_FOUND.getKey()), eq(patientId))).thenReturn(expectedMessage);
        try {
            patientDeletionService.deletePatientById(patientId);
            fail(TestApplicationConstants.EXPECTED_DATA_NOT_FOUND_EXCEPTION);
        } catch (DataNotFoundException exception) {
            assertEquals(expectedMessage, exception.getMessage());
        }
    }

    /**
     * Test method for testing the validateDoctorDetails method of Doctor Validation
     * HTTP Status code-409
     */
    @Test
    void testDeletePatientById_PatientAlreadyAssigned() throws Exception {
        String patientId = UUID.randomUUID().toString();
        PatientModel mockPatient = new PatientModel();
        String expectedMessage = new StringBuilder().append(TestApplicationConstants.NO_PATIENT_FOUND_WITH_ID).append(patientId).toString();
        when(patientRepository.findById(UUID.fromString(patientId))).thenReturn(Optional.of(mockPatient));
        when(doctorPatientAssignmentRepository.findByPatientId(UUID.fromString(patientId))).thenReturn(List.of(new DoctorPatientAssignmentModel()));
        when(messageUtil.getMessage(MessageKeyEnum.PATIENT_ASSIGNED_TO_DOCTOR.getKey())).thenReturn(expectedMessage);
        try {
            patientDeletionService.deletePatientById(patientId);
            fail(TestApplicationConstants.EXPECTED_PATIENT_ALREADY_ASSIGNED_EXCEPTION);
        } catch (PatientAlreadyAssignedException exception) {
            assertEquals(expectedMessage, exception.getMessage());
        }
    }
}
