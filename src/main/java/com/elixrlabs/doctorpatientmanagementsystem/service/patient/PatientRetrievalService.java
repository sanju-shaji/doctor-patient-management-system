package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.ResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import io.micrometer.common.util.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Class for GetByName/Patient Module
 */
@Service
public class PatientRetrievalService {
    private final PatientRepository repository;
    private final PatientValidation patientValidation;

    public PatientRetrievalService(PatientRepository repository, PatientValidation patientValidation) {
        this.repository = repository;
        this.patientValidation = patientValidation;
    }

    public ResponseEntity<PatientResponseDto> getPatientsByNamePrefixWithValidation(String name) {
        List<String> errors = patientValidation.validatePatientName(name);
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PatientResponseDto(errors, false));
        }
        String[] names = separateFirstAndLastName(name);
        List<PatientDto> patients = getPatientsByNamePrefix(name);
        if (!names[1].isEmpty()) {
            patients = getPatientsByFirstAndLastName(names[0], names[1]);
        } else {
            patients = getPatientsByNamePrefix(names[0]);
        }
        if (patients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PatientResponseDto(List.of(ApplicationConstants.NO_PATIENTS_FOUND), false));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new PatientResponseDto(true, patients));
    }

    /**
     * Retrieves patient by UUId after performing validations.
     *
     * @param id the UUID of the patient to retrieve.
     * @return ResponseEntity on containing the patient data on success and an error on failure.
     */
    public ResponseEntity<ResponseDto> getPatientById(String id) {
        try {
            List<String> validationErrors = patientValidation.validatePatientId(id);
            if (!validationErrors.isEmpty()) {
                ResponseDto errorResponse = ResponseDto.builder()
                        .success(false)
                        .errors(validationErrors)
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(errorResponse);
            }
            UUID patientId = UUID.fromString(id);
            Optional<PatientModel> patientOptional = repository.findById(patientId);
            if (patientOptional.isPresent()) {
                PatientModel patient = patientOptional.get();
                ResponseDto responseDto = ResponseDto.builder()
                        .success(true)
                        .data(patient)
                        .build();
                return ResponseEntity.ok(responseDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDto.builder()
                        .success(false)
                        .errors(List.of(ApplicationConstants.PATIENT_NOT_FOUND + id))
                        .build());
            }
        } catch (Exception exception) {
            ResponseDto responseDto = ResponseDto.builder()
                    .success(false)
                    .errors(List.of(ApplicationConstants.SERVER_ERROR + exception.getMessage()))
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }

    private List<PatientDto> getPatientsByNamePrefix(String name) {
        List<PatientModel> patients = repository.findByFirstNameStartingWithIgnoreCaseOrLastNameStartingWithIgnoreCase(name, name);
        return patients.stream().map(patientModel -> new PatientDto(patientModel)
        ).collect(Collectors.toList());
    }

    private List<PatientDto> getPatientsByFirstAndLastName(String firstName, String lastName) {
        List<PatientModel> patients = repository.findByFirstNameStartingWithIgnoreCaseAndLastNameStartingWithIgnoreCase(firstName, lastName);
        return patients.stream().map(patientModel -> new PatientDto(patientModel)
        ).collect(Collectors.toList());
    }

    private String[] separateFirstAndLastName(String name) {
        String[] names = name.trim().split(" ");
        System.out.println(names);
        String firstName = names[0];
        String lastName = names.length > 1 ? names[1] : "";
        return new String[]{firstName, lastName};
    }
}
