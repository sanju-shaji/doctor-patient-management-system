package com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.model.doctorpatientassignment.DoctorPatientAssignmentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository layer interface for DoctorPatientAssignment collection
 */
@Repository
public interface DoctorPatientAssignmentRepository extends MongoRepository<DoctorPatientAssignmentModel, UUID> {

    List<DoctorPatientAssignmentModel> findByPatientId(UUID patientId);

    List<DoctorPatientAssignmentModel> findByDoctorId(UUID doctorId);

    Optional<Object> findByDoctorIdAndPatientId(UUID doctorId, UUID patientId);
}
