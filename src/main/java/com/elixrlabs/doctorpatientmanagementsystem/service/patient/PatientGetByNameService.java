package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PatientGetByNameService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<PatientDto> getPatientsByNamePrefix(String name) {
        List<PatientModel> patients = repository.findByPatientFirstNameStartingWithIgnoreCaseOrPatientLastNameStartingWithIgnoreCase(name, name);
        return patients.stream().map(patientModel -> new PatientDto(patientModel.getId(), patientModel.getPatientFirstName(), patientModel.getPatientLastName())
        ).collect(Collectors.toList());
    }
}
