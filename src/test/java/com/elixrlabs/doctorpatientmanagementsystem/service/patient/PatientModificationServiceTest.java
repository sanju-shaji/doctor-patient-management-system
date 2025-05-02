package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidJsonOperationException;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatchPatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.util.TestDataBuilder;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonpatch.JsonPatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

public class PatientModificationServiceTest {

    private TestDataBuilder testDataBuilder;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private MessageUtil messageUtil;

    @Mock
    private PatientValidation patientValidation;

    @InjectMocks
    private PatientModificationService patientModificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testDataBuilder = new TestDataBuilder();
    }

    @Test
    public void applyPatch_whenPatientNotFound_shouldThrowDataNotFoundException() throws Exception {
        String patientId = UUID.randomUUID().toString();
        JsonPatch patch = Mockito.mock(JsonPatch.class);
        Mockito.doNothing().when(patientValidation).validatePatientId(patientId);
        Mockito.when(messageUtil.getMessage(MessageKeyEnum.NO_PATIENT_FOUND.getKey(), patientId)).thenReturn(TestApplicationConstants.PATIENT_NOT_FOUND);
        try {
            patientModificationService.applyPatch(patientId, patch);
            Assertions.fail(TestApplicationConstants.EXPECTED_DATA_NOT_FOUND_EXCEPTION);
        } catch (DataNotFoundException exception) {
            Assertions.assertEquals(TestApplicationConstants.PATIENT_NOT_FOUND, exception.getMessage());
        }
        Mockito.verify(patientRepository).findById(UUID.fromString(patientId));
    }

    @Test
    public void applyPatch_SuccessfulPatch_ReturnsUpdatedPatient() throws Exception {
        String patientId = UUID.randomUUID().toString();
        String patchString = TestApplicationConstants.PATCH_CONTENT;
        JsonPatch patch = JsonPatch.fromJson(JsonLoader.fromString(patchString));

        PatientModel existingPatient = testDataBuilder.patientModelBuilder();

        PatientDto patchedDto = testDataBuilder.patchedDtoBuilder();

        JsonNode patientNode = new ObjectMapper().valueToTree(patchedDto);
        JsonNode patchedNode = new ObjectMapper().valueToTree(patchedDto);

        PatientModel savedPatient = testDataBuilder.savedPatientBuilder();

        Mockito.when(patientRepository.findById(UUID.fromString(patientId))).thenReturn(Optional.of(existingPatient));
        Mockito.when(objectMapper.convertValue(existingPatient, PatientDto.class)).thenReturn(patchedDto);
        Mockito.when(objectMapper.convertValue(patchedDto, JsonNode.class)).thenReturn(patientNode);
        Mockito.when(objectMapper.treeToValue(patchedNode, PatientDto.class)).thenReturn(patchedDto);
        Mockito.when(patientValidation.validatePatients(patchedDto)).thenReturn(Collections.emptyList());
        Mockito.when(objectMapper.convertValue(Mockito.any(PatientDto.class), Mockito.eq(PatientModel.class))).thenReturn(savedPatient);
        Mockito.when(patientRepository.save(savedPatient)).thenReturn(savedPatient);

        ResponseEntity<PatchPatientResponse> response = patientModificationService.applyPatch(patientId, patch);

        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertTrue(response.getBody().isSuccess());
        Assertions.assertEquals(TestApplicationConstants.EXPECTED_FIRST_NAME, response.getBody().getPatient().getFirstName());
    }

    @Test
    public void applyPatch_ValidationFails_ThrowsInvalidJsonOperationException() throws Exception {
        String patientId = UUID.randomUUID().toString();
        String patchContent = TestApplicationConstants.PATCH_INVALID_CONTENT;
        JsonPatch patch = JsonPatch.fromJson(JsonLoader.fromString(patchContent));

        PatientModel existingPatient = testDataBuilder.patientModelBuilder();

        PatientDto patchedDto = testDataBuilder.invalidPatchedDtoBuilder();

        JsonNode patientNode = new ObjectMapper().valueToTree(patchedDto);
        JsonNode patchedNode = new ObjectMapper().valueToTree(patchedDto);

        Mockito.when(patientRepository.findById(UUID.fromString(patientId))).thenReturn(Optional.of(existingPatient));
        Mockito.when(objectMapper.convertValue(existingPatient, PatientDto.class)).thenReturn(patchedDto);
        Mockito.when(objectMapper.convertValue(patchedDto, JsonNode.class)).thenReturn(patientNode);
        Mockito.when(objectMapper.treeToValue(patchedNode, PatientDto.class)).thenReturn(patchedDto);
        Mockito.when(patientValidation.validatePatients(patchedDto)).thenReturn(Collections.singletonList(TestApplicationConstants.INVALID_FIRST_NAME));
        InvalidJsonOperationException thrownException = null;
        try {
            patientModificationService.applyPatch(patientId, patch);
        } catch (InvalidJsonOperationException Exception) {
            thrownException = Exception;
        }
        Assertions.assertNotNull(thrownException);
        Assertions.assertTrue(thrownException.getErrors().contains(TestApplicationConstants.INVALID_FIRST_NAME));
    }
}
