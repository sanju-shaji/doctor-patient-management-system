package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.PatientNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.PatientValidationException;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Class for GetByName/Patient Module
 */
@Service
public class PatientRetrievalService {
    private final PatientRepository patientRepository;
    private final PatientValidation patientValidation;
    private final MessageUtil messageUtil;

    public PatientRetrievalService(PatientRepository patientRepository, PatientValidation patientValidation, MessageUtil messageUtil) {
        this.patientRepository = patientRepository;
        this.patientValidation = patientValidation;
        this.messageUtil = messageUtil;
    }

    public ResponseEntity<PatientResponseDto> getPatientsByNamePrefixWithValidation(String name) throws Exception {
        List<String> errors = patientValidation.validatePatientName(name);
        if (!errors.isEmpty()) {
            String message = messageUtil.getMessage(MessageKeyEnum.QUERY_PARAMS_CANNOT_BE_NULL.getKey());
            throw new PatientValidationException(message);
        }
        String[] names = separateFirstAndLastName(name);
        getPatientsByNamePrefix(name);
        List<PatientDto> patients;
        if (!names[1].isEmpty()) {
            patients = getPatientsByFirstAndLastName(names[0], names[1]);
        } else {
            patients = getPatientsByNamePrefix(names[0]);
        }
        if (patients.isEmpty()) {
            String message = messageUtil.getMessage(MessageKeyEnum.NO_PATIENT_FOUND.getKey());
            throw new PatientNotFoundException(message);
        }
        PatientResponseDto patientResponseDto = PatientResponseDto.builder()
                .success(true)
                .patients(patients)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(patientResponseDto);
    }

    /**
     * Retrieves patient by UUId after performing validations.
     *
     * @param id the UUID of the patient to retrieve.
     * @return ResponseEntity on containing the patient data on success and an error on failure.
     */
    public ResponseEntity<PatientResponse> getPatientById(String id) throws Exception {
        patientValidation.validatePatientId(id);
        UUID patientId = UUID.fromString(id);
        Optional<PatientModel> patientOptional = patientRepository.findById(patientId);
        if (patientOptional.isPresent()) {
            PatientModel patientModel = patientOptional.get();
            PatientResponse patientResponse = PatientResponse.builder()
                    .success(true)
                    .data(patientModel)
                    .build();
            return ResponseEntity.ok(patientResponse);
        }
        throw new DataNotFoundException(ApplicationConstants.PATIENT_ID_NOT_FOUND, patientId);
    }

    private List<PatientDto> getPatientsByNamePrefix(String name) {
        List<PatientModel> patients = patientRepository.findByFirstNameStartingWithIgnoreCaseOrLastNameStartingWithIgnoreCase(name, name);
        List<PatientDto> patientDtoList = new ArrayList<>();
        for (PatientModel patientModel : patients) {
            PatientDto patientDto = new PatientDto(patientModel);
            patientDtoList.add(patientDto);
        }
        return patientDtoList;
    }

    private List<PatientDto> getPatientsByFirstAndLastName(String firstName, String lastName) {

        List<PatientModel> patients = patientRepository.findByFirstNameStartingWithIgnoreCaseOrLastNameStartingWithIgnoreCase(firstName, lastName);
        List<PatientDto> patientDtoList = new ArrayList<>();
        for (PatientModel patientModel : patients) {
            PatientDto patientDto = new PatientDto(patientModel);
            patientDtoList.add(patientDto);
        }
        return patientDtoList;
    }

    private String[] separateFirstAndLastName(String name) {
        String[] names = name.trim().split(" ");
        String firstName = names[0];
        String lastName = names.length > 1 ? names[1] : "";
        return new String[]{firstName, lastName};
    }
}
