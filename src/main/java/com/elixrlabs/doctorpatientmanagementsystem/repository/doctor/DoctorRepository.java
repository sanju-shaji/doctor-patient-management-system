package com.elixrlabs.doctorpatientmanagementsystem.repository.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DataBaseConstants;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for performing database operations on DoctorEntity.
 */
@Repository
public interface DoctorRepository extends MongoRepository<DoctorEntity, UUID> {
    @Query(DataBaseConstants.QUERY_STRING)
    List<DoctorEntity> findByName(String name);
}
