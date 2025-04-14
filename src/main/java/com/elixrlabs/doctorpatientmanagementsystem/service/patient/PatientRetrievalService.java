package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.DoctorWithPatientsDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.DoctorPatientAssignmentResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.validation.patient.PatientValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Class responsible for retrieving patient data.
 * Handles operations such as:
 * Searching patients by name.
 * Retrieving a specific patient by id.
 * Fetching list of patients assigned to a specific doctor.
 */
@Service
public class PatientRetrievalService {
    private final PatientRepository patientRepository;
    private final PatientValidation patientValidation;
    private final MessageUtil messageUtil;
    private final DoctorRepository doctorRepository;
    public PatientRetrievalService(PatientRepository patientRepository, PatientValidation patientValidation,MessageUtil messageUtil,DoctorRepository doctorRepository  ){
        this.patientRepository=patientRepository;
        this.patientValidation=patientValidation;
        this.messageUtil=messageUtil;
        this.doctorRepository=doctorRepository;
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
            PatientResponse patientResponse = PatientResponse.builder()
                    .success(true)
                    .data(patientModel)
                    .build();
            return ResponseEntity.ok(patientResponse);
        }
        throw new DataNotFoundException(MessageKeyEnum.PATIENT_ID_NOT_FOUND.getKey(), patientId);
    }

    /**
     * Retrieves the doctor along with their assigned patients using doctorId
     */
    public ResponseEntity<DoctorPatientAssignmentResponse> getPatientsWithDoctor(String doctorId) throws Exception {
        UUID doctorUuid = UUID.fromString(doctorId);
        if (!doctorRepository.existsById(doctorUuid)) {
            throw new DataNotFoundException(messageUtil.getMessage(MessageKeyEnum.DOCTOR_NOT_FOUND_ERROR.getKey()), doctorUuid);
        }
        DoctorWithPatientsDto assignedPatientsToDoctorData = doctorRepository.getAssignedPatientsByDoctorId(doctorUuid);
        if(assignedPatientsToDoctorData.getPatients().isEmpty()){
            throw new DataNotFoundException(messageUtil.getMessage(MessageKeyEnum.DOCTOR_NOT_ASSIGNED.getKey()), doctorUuid);
        }
        DoctorPatientAssignmentResponse doctorPatientAssignmentResponse = DoctorPatientAssignmentResponse.builder()
                .id(assignedPatientsToDoctorData.getId())
                .firstName(assignedPatientsToDoctorData.getFirstName())
                .lastName(assignedPatientsToDoctorData.getLastName())
                .department(assignedPatientsToDoctorData.getDepartment())
                .patients(assignedPatientsToDoctorData.getPatients())
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(doctorPatientAssignmentResponse);
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
