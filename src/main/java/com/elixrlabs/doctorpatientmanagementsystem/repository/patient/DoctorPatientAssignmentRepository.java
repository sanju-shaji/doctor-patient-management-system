package com.elixrlabs.doctorpatientmanagementsystem.repository.patient;

import com.elixrlabs.doctorpatientmanagementsystem.model.patient.DoctorPatientAssignmentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository layer interface for DoctorPatientAssignmentRepository entity
 */
@Repository
public interface DoctorPatientAssignmentRepository extends MongoRepository<DoctorPatientAssignmentModel, UUID> {
    List<DoctorPatientAssignmentModel> findByPatientId(UUID patientId);
}
