package com.elixrlabs.doctorpatientmanagementsystem.validation.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DoctorAlreadyUnassignedException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidAssignmentDataException;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctorpatientassignment.DoctorPatientAssignmentModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment.DoctorPatientAssignmentRepository;
import com.elixrlabs.doctorpatientmanagementsystem.repository.patient.PatientRepository;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Validator class for handling validations related to doctor-patient UnAssignment operations
 */
@Component
public class DoctorPatientUnAssignmentValidator {
    private final DoctorPatientAssignmentRepository doctorPatientAssignmentRepository;
    private final MessageUtil messageUtil;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public DoctorPatientUnAssignmentValidator(DoctorPatientAssignmentRepository doctorPatientAssignmentRepository, MessageUtil messageUtil, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.doctorPatientAssignmentRepository = doctorPatientAssignmentRepository;
        this.messageUtil = messageUtil;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    /**
     * Validates whether the given doctor and patient combination is valid and not already unassigned
     *
     * @param doctorId  UUID of the doctor
     * @param patientId UUID of the patient
     * @throws DataNotFoundException            if no assignment is found for the given doctor-patient combination
     * @throws DoctorAlreadyUnassignedException if the doctor-patient combination is already unassigned
     */
    public void validateDoctorPatientCombination(UUID doctorId, UUID patientId) throws DataNotFoundException, DoctorAlreadyUnassignedException {
        List<String> errors = new ArrayList<>();
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
        Optional<DoctorPatientAssignmentModel> assignments = doctorPatientAssignmentRepository.findByDoctorIdAndPatientIdAndIsUnAssigned(doctorId, patientId,false);
        if (assignments.isEmpty()) {
            throw new DataNotFoundException(messageUtil.getMessage(MessageKeyEnum.DOCTOR_PATIENT_COMBINATION_NOT_FOUND.getKey()));
        }
        if (assignments.get().isUnAssigned()) {
            throw new DoctorAlreadyUnassignedException(messageUtil.getMessage(MessageKeyEnum.DOCTOR_ALREADY_UNASSIGNED.getKey(), patientId));
        }
    }
}
