package com.elixrlabs.doctorpatientmanagementsystem.repository.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for doctor entity
 */
@Repository
public interface DoctorRepository extends MongoRepository<DoctorEntity, UUID> {
}
