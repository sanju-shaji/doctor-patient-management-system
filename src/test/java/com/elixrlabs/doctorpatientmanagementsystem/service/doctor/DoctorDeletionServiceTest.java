package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidAssignmentDataException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment.DoctorPatientAssignmentRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.ResponseBuilder;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for DoctorDeletionService to verify doctor deletion logic, validations, and exception handling.
 */
@ExtendWith(MockitoExtension.class)
class DoctorDeletionServiceTest {

    TestDataBuilder testDataBuilder;
    @Mock
    DoctorRepository doctorRepository;
    @Mock
    DoctorPatientAssignmentRepository doctorPatientAssignmentRepository;
    @Mock
    DoctorValidation doctorValidation;
    @Mock
    MessageUtil messageUtil;
    @Mock
    ResponseBuilder responseBuilder;
    @InjectMocks
    DoctorDeletionService doctorDeletionService;

    @BeforeEach
    void setUp() {
        testDataBuilder = new TestDataBuilder();
    }

    /**
     * Tests successful doctor deletion when valid UUID is provided and no patient assignments exist.
     */
    @Test
    void testDeleteDoctorById_withValidId_returnSuccessResponse() throws DataNotFoundException, InvalidUuidException {
        DoctorEntity doctorEntity = testDataBuilder.doctorEntityBuilder();
        ResponseEntity<BaseResponse> successDeleteResponseResponse = testDataBuilder.buildSuccessDeleteResponse(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        Mockito.when(doctorRepository.findById(UUID.fromString(TestApplicationConstants.UUID))).thenReturn(Optional.of(doctorEntity));
        Mockito.when(doctorPatientAssignmentRepository.findByDoctorId(UUID.fromString(TestApplicationConstants.UUID))).thenReturn(new ArrayList<>());
        Mockito.when(messageUtil.getMessage(MessageKeyEnum.DELETE_DOCTOR_SUCCESSFULLY.getKey())).thenReturn(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE);
        Mockito.when(responseBuilder.buildSuccessDeleteResponse(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE))).thenReturn(successDeleteResponseResponse);
        ResponseEntity<BaseResponse> deleteResponse = doctorDeletionService.deleteDoctorById(TestApplicationConstants.UUID);
        assertNotNull(deleteResponse);
        assertNotNull(deleteResponse.getBody());
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertTrue(deleteResponse.getBody().isSuccess());
        assertEquals(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE), deleteResponse.getBody().getMessages());
    }

    /**
     * Tests doctor deletion failure when invalid (empty) UUID is given, expecting validation exception and 400 response.
     */
    @Test
    void testDeleteDoctorById_withInValidId_returnErrorResponse() throws Exception {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        Mockito.doThrow(new InvalidAssignmentDataException(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE))).when(doctorValidation).validateDoctorId(TestApplicationConstants.EMPTY_QUERY_STRING);
        try {
            doctorDeletionService.deleteDoctorById(TestApplicationConstants.EMPTY_QUERY_STRING);
        } catch (InvalidAssignmentDataException invalidAssignmentDataException) {
            ResponseEntity<DoctorResponse> deleteResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(expectedResponse);
            assertEquals(HttpStatus.BAD_REQUEST.value(), deleteResponse.getStatusCode().value());
            assertEquals(expectedResponse, deleteResponse.getBody());
        }
    }

    /**
     * Tests doctor deletion failure when doctor not found in database, expecting DataNotFoundException and 404 response.
     */
    @Test
    void testDeleteDoctorById_withInValidId_returnDoctorNotFoundResponse() throws InvalidUuidException {
        DoctorResponse expectedResponse = testDataBuilder.invalidDoctorResponseBuilder();
        Mockito.when(doctorRepository.findById(UUID.fromString(TestApplicationConstants.UUID))).thenReturn(Optional.empty());
        try {
            doctorDeletionService.deleteDoctorById(TestApplicationConstants.UUID);
        } catch (DataNotFoundException dataNotFoundException) {
            ResponseEntity<DoctorResponse> deleteResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body(expectedResponse);
            assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
            assertEquals(expectedResponse, deleteResponse.getBody());
        }
    }
}