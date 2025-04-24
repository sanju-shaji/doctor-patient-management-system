package com.elixrlabs.doctorpatientmanagementsystem.validation.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.enums.MessageKeyEnum;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DoctorAlreadyUnassignedException;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctorpatientassignment.DoctorPatientAssignmentModel;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment.DoctorPatientAssignmentRepository;
import com.elixrlabs.doctorpatientmanagementsystem.util.MessageUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Validator class for handling validations related to doctor-patient UnAssignment operations
 */
@Component
public class DoctorPatientUnAssignmentValidator {
    private final DoctorPatientAssignmentRepository doctorPatientAssignmentRepository;
    private final MessageUtil messageUtil;
    public DoctorPatientUnAssignmentValidator(DoctorPatientAssignmentRepository doctorPatientAssignmentRepository, MessageUtil messageUtil){
        this.doctorPatientAssignmentRepository=doctorPatientAssignmentRepository;
        this.messageUtil=messageUtil;
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
        Optional<DoctorPatientAssignmentModel> assignments = doctorPatientAssignmentRepository.findByDoctorIdAndPatientId(doctorId, patientId);
        if (assignments.isEmpty()) {
            throw new DataNotFoundException(messageUtil.getMessage(MessageKeyEnum.DOCTOR_PATIENT_COMBINATION_NOT_FOUND.getKey()));
        }
        if (assignments.get().isUnAssigned()) {
            throw new DoctorAlreadyUnassignedException(messageUtil.getMessage(MessageKeyEnum.DOCTOR_ALREADY_UNASSIGNED.getKey(), patientId));
        }
    }
}
