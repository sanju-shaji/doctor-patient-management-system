package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PostPatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.ResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service class responsible for handling business logic related to patient creation
 */
@Service
public class PatientCreationService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientValidation patientValidation;

    /**
     * creates a new patient after performing validations
     *
     * @param postPatientDto
     * @return
     */
    public ResponseDto createPatient(PostPatientDto postPatientDto) {
        // Perform validation
        List<String> validationErrors = patientValidation.validatePatient(postPatientDto);
        if (!validationErrors.isEmpty()) {
            return new ResponseDto(validationErrors);
        }
        UUID patientId = UUID.randomUUID();
        PatientModel patient = new PatientModel(
                patientId,
                postPatientDto.getPatientFirstName().trim(),
                postPatientDto.getPatientLastName().trim()
        );
        patientRepository.save(patient);
        return new ResponseDto(patientId.toString(), patient.getPatientFirstName().trim(), patient.getPatientLastName().trim());
    }
}
