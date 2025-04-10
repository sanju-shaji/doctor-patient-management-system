package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
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
    private final PatientRepository patientRepository;
    private final PatientValidation patientValidation;

    public PatientRetrievalService(PatientRepository repository, PatientValidation patientValidation) {
        this.patientRepository = repository;
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
    public ResponseEntity<PatientResponse> getPatientById(String id) throws Exception {
        patientValidation.validatePatientId(id);
        UUID patientId = UUID.fromString(id);
        Optional<PatientModel> patientOptional = patientRepository.findById(patientId);
        if (patientOptional.isPresent()) {
            PatientModel patientModel = patientOptional.get();
            PatientResponse responseDto = PatientResponse.builder()
                    .success(true)
                    .data(patientModel)
                    .build();
            return ResponseEntity.ok(responseDto);
        }
        throw new DataNotFoundException(ApplicationConstants.PATIENT_NOT_FOUND, patientId);
    }

    private List<PatientDto> getPatientsByNamePrefix(String name) {
        List<PatientModel> patients = patientRepository.findByFirstNameStartingWithIgnoreCaseOrLastNameStartingWithIgnoreCase(name, name);
        return patients.stream().map(patientModel -> new PatientDto(patientModel)
        ).collect(Collectors.toList());
    }

    private List<PatientDto> getPatientsByFirstAndLastName(String firstName, String lastName) {
        List<PatientModel> patients = patientRepository.findByFirstNameStartingWithIgnoreCaseAndLastNameStartingWithIgnoreCase(firstName, lastName);
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
