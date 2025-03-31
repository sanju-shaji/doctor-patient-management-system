package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PostPatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.ResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDto> createPatient(PostPatientDto postPatientDto) {
        // Perform validation
        List<String> validationErrors = patientValidation.validatePatient(postPatientDto);

        if (!validationErrors.isEmpty()) {
            ResponseDto errorResponse = new ResponseDto(false, validationErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        UUID patientId = UUID.randomUUID();
        PatientModel patient = new PatientModel(
                patientId,
                postPatientDto.getPatientFirstName().trim(),
                postPatientDto.getPatientLastName().trim()
        );
        patientRepository.save(patient);
        ResponseDto successResponse = new ResponseDto(true, patientId.toString(),patient.getPatientFirstName(),patient.getPatientLastName());
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }
}
