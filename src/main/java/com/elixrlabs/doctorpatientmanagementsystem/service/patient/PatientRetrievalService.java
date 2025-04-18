package com.elixrlabs.doctorpatientmanagementsystem.service.patient;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.DoctorWithAssignedPatientsData;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientResponseDto;
import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.PatientNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.PatientValidationException;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.DoctorWithAssignedPatientsResponse;
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

    public PatientRetrievalService(PatientRepository patientRepository,
                                   PatientValidation patientValidation,
                                   MessageUtil messageUtil,
                                   DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.patientValidation = patientValidation;
        this.messageUtil = messageUtil;
        this.doctorRepository = doctorRepository;
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
        throw new DataNotFoundException(messageUtil.getMessage(MessageKeyEnum.PATIENT_ID_NOT_FOUND.getKey(), patientId));
    }

    /**
     * Retrieves the doctor along with their assigned patients using doctorId
     */
    public ResponseEntity<DoctorWithAssignedPatientsResponse> getPatientsWithDoctor(String doctorId) throws Exception {
        patientValidation.validatePatientId(doctorId);
        UUID doctorUuid = UUID.fromString(doctorId);
        if (!doctorRepository.existsById(doctorUuid)) {
            throw new DataNotFoundException(messageUtil.getMessage(MessageKeyEnum.DOCTOR_NOT_FOUND_ERROR.getKey(), doctorUuid));
        }
        DoctorWithAssignedPatientsData assignedPatientsToDoctorData = doctorRepository.getAssignedPatientsByDoctorId(doctorUuid);
        if (assignedPatientsToDoctorData == null || assignedPatientsToDoctorData.getPatients().get(0).getId() == null) {
            throw new DataNotFoundException(messageUtil.getMessage(MessageKeyEnum.DOCTOR_NOT_ASSIGNED.getKey(), doctorUuid));
        }
        DoctorWithAssignedPatientsResponse doctorWithAssignedPatientsResponse = DoctorWithAssignedPatientsResponse.builder()
                .id(assignedPatientsToDoctorData.getId())
                .firstName(assignedPatientsToDoctorData.getFirstName())
                .lastName(assignedPatientsToDoctorData.getLastName())
                .department(assignedPatientsToDoctorData.getDepartment())
                .patients(assignedPatientsToDoctorData.getPatients())
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(doctorWithAssignedPatientsResponse);
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

        List<PatientModel> patients = patientRepository.findByFirstNameStartingWithIgnoreCaseAndLastNameStartingWithIgnoreCase(firstName, lastName);
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
