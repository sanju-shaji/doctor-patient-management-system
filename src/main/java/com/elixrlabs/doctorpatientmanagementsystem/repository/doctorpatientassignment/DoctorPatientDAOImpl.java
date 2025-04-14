package com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.DoctorPatientAssignmentDto;
import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

@RequiredArgsConstructor
@Repository
public class DoctorPatientDAOImpl implements DoctorPatientDAO {
private final MongoTemplate mongoTemplate;
    @Override
    public DoctorPatientAssignmentDto getAssignedDoctorsByPatientId(UUID id) {
        Aggregation aggregation = newAggregation(
                Aggregation.match(Criteria.where(ApplicationConstants.ID).is(id)),
                Aggregation.lookup(ApplicationConstants.ASSIGNMENT_COLLECTION, ApplicationConstants.ID, ApplicationConstants.PATIENT_ID, ApplicationConstants.ASSIGNMENTS),
                unwind(ApplicationConstants.ASSIGNMENTS, true),
                Aggregation.lookup(ApplicationConstants.DOCTORS_COLLECTION, ApplicationConstants.ASSIGNMENTS_DOCTOR_ID, ApplicationConstants.ID, ApplicationConstants.ASSIGNMENTS_DOCTOR),
                unwind(ApplicationConstants.ASSIGNMENTS_DOCTOR, true),
                group(ApplicationConstants.ID).first(ApplicationConstants.FIRST_NAME).as(ApplicationConstants.FIRST_NAME)
                        .first(ApplicationConstants.LAST_NAME).as(ApplicationConstants.LAST_NAME)
                        .push(ApplicationConstants.ASSIGNMENTS_DOCTOR).as(ApplicationConstants.DOCTORS)
        );
        AggregationResults<DoctorPatientAssignmentDto> results = mongoTemplate.aggregate(aggregation, ApplicationConstants.PATIENT_COLLECTION, DoctorPatientAssignmentDto.class);
        return results.getUniqueMappedResult();
    }
}
