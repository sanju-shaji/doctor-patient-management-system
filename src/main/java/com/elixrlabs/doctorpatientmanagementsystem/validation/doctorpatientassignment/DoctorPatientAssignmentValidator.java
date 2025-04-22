package com.elixrlabs.doctorpatientmanagementsystem.validation.doctorpatientassignment;


import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.DoctorPatientAssignmentDto;

import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidAssignmentDataException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.PatientAlreadyAssignedException;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment.DoctorPatientAssignmentRepository;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DoctorPatientAssignmentValidator {
    private final DoctorValidation doctorValidation;
    private final DoctorRepository doctorRepository;
    private final MessageUtil messageUtil;
    private final PatientRepository patientRepository;
    private final DoctorPatientAssignmentRepository doctorPatientAssignmentRepository;

    public DoctorPatientAssignmentValidator(DoctorValidation doctorValidation, DoctorRepository doctorRepository, MessageUtil messageUtil, PatientRepository patientRepository, DoctorPatientAssignmentRepository doctorPatientAssignmentRepository) {
        this.doctorValidation = doctorValidation;
        this.doctorRepository = doctorRepository;
        this.messageUtil = messageUtil;
        this.patientRepository = patientRepository;
        this.doctorPatientAssignmentRepository = doctorPatientAssignmentRepository;
    }

    public void validateAssignmentDto(DoctorPatientAssignmentDto assignmentDto) {
        List<String> errors = new ArrayList<>();
        doctorValidation.validateDoctorAndPatientIds(assignmentDto.getDoctorId(), assignmentDto.getPatientId());
        UUID doctorId = UUID.fromString(assignmentDto.getDoctorId());
        UUID patientId = UUID.fromString(assignmentDto.getPatientId());
        if (doctorRepository.findById(doctorId).isEmpty()) {
            String message = messageUtil.getMessage(MessageKeyEnum.NO_DOCTOR_FOUND.getKey(), doctorId);
            errors.add(message);
        }
        if (patientRepository.findById(patientId).isEmpty()) {
            String message = messageUtil.getMessage(MessageKeyEnum.NO_PATIENT_FOUND.getKey(), patientId);
            errors.add(message);
        }
        if (!errors.isEmpty()) {
            throw new InvalidAssignmentDataException(errors);
        }
        if (doctorPatientAssignmentRepository.findByDoctorIdAndPatientId(doctorId, patientId).isPresent()) {
            throw new PatientAlreadyAssignedException(messageUtil.getMessage(MessageKeyEnum.ASSIGNMENT_FAILED.getKey()));
        }
    }
}
