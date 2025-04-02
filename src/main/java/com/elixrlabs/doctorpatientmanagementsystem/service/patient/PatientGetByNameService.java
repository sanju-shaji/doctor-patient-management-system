package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.PatientGetByNameConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validations.patient.PatientGetByNameValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Class for GetByName/Patient Module
 */
@Service
public class PatientGetByNameService {
    private final PatientRepository repository;
    @Autowired
    PatientGetByNameValidations patientGetByNameValidations;

    public PatientGetByNameService(PatientRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<PatientResponseDto> getPatientsByNamePrefixWithValidation(String name) {
        List<String> errors = patientGetByNameValidations.validatePatientName(name);
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PatientResponseDto(List.of(PatientGetByNameConstants.NO_PATIENTS_FOUND), false));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new PatientResponseDto(true, patients));
    }

    private List<PatientDto> getPatientsByNamePrefix(String name) {
        List<PatientModel> patients = repository.findByPatientFirstNameStartingWithIgnoreCaseOrPatientLastNameStartingWithIgnoreCase(name, name);
        return patients.stream().map(patientModel -> new PatientDto(patientModel)
        ).collect(Collectors.toList());
    }

    private List<PatientDto> getPatientsByFirstAndLastName(String firstName, String lastName) {
        List<PatientModel> patients = repository.findByPatientFirstNameStartingWithIgnoreCaseAndPatientLastNameStartingWithIgnoreCase(firstName, lastName);
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
