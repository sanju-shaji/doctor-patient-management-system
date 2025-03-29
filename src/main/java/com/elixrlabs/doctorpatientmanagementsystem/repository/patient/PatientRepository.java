package com.elixrlabs.doctorpatientmanagementsystem.repository.patient;

import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
/**
 * Repository layer interface for patient entity
 */
@Repository
public interface PatientRepository extends MongoRepository<PatientModel, UUID> {
    List<PatientModel> findByPatientFirstNameStartingWithIgnoreCaseOrPatientLastNameStartingWithIgnoreCase(String firstName, String lastName);
}
