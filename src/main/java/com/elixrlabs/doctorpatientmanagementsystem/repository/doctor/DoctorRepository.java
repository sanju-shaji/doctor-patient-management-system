package com.elixrlabs.doctorpatientmanagementsystem.repository.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DataBaseConstants;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment.DoctorWithPatientsDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for performing database operations on DoctorEntity.
 */
@Repository
public interface DoctorRepository extends MongoRepository<DoctorEntity, UUID>, DoctorWithPatientsDAO {
    @Query(DataBaseConstants.FETCH_DOCTOR_BY_FIRSTNAME_OR_LASTNAME_QUERY)
    List<DoctorEntity> findByName(String name);
}
