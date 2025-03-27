package com.elixrlabs.doctorpatientmanagementsystem.repository.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * Repository class for doctor entity
 */
public interface DoctorRepository extends MongoRepository<DoctorEntity, UUID> {
}
