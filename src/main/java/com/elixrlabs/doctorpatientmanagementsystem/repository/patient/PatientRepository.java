package com.elixrlabs.doctorpatientmanagementsystem.repository.patient;

import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PatientRepository extends MongoRepository<PatientModel, UUID> {
}
