package com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.AssignedDoctorsToPatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

/**
 * Implementation class for DoctorPatientDAO interface
 */
@RequiredArgsConstructor
@Repository
public class DoctorPatientDAOImpl implements DoctorPatientDAO {
    private final MongoTemplate mongoTemplate;

    /**
     * method to implement aggregation query for getAssignedDoctorsByPatient api
     *
     * @param id-patient id
     * @return - patient data with list of doctors mapped to DoctorPatientAssignmentDto
     */
    @Override
    public AssignedDoctorsToPatientDto getAssignedDoctorsByPatientId(UUID id) {
        Aggregation aggregation = newAggregation(
                Aggregation.match(Criteria.where(ApplicationConstants.ID).is(id)),
                Aggregation.lookup(ApplicationConstants.ASSIGNMENT_COLLECTION, ApplicationConstants.ID, ApplicationConstants.PATIENT_ID, ApplicationConstants.ASSIGNMENTS),
                unwind(ApplicationConstants.ASSIGNMENTS, true),
                Aggregation.match(Criteria.where(ApplicationConstants.ASSIGNMENTS_IS_UNASSIGNED).is(false)),
                Aggregation.lookup(ApplicationConstants.DOCTORS_COLLECTION, ApplicationConstants.ASSIGNMENTS_DOCTOR_ID, ApplicationConstants.ID, ApplicationConstants.ASSIGNMENTS_DOCTOR),
                unwind(ApplicationConstants.ASSIGNMENTS_DOCTOR, true),
                group(ApplicationConstants.ID).first(ApplicationConstants.FIRST_NAME).as(ApplicationConstants.FIRST_NAME)
                        .first(ApplicationConstants.LAST_NAME).as(ApplicationConstants.LAST_NAME)
                        .push(new Document().append(ApplicationConstants.ID, ApplicationConstants.ASSIGNMENTS_DOCTOR_ID_VALUE)
                                .append(ApplicationConstants.FIRST_NAME, ApplicationConstants.ASSIGNMENTS_DOCTOR_FIRSTNAME_VALUE).append(ApplicationConstants.LAST_NAME,
                                        ApplicationConstants.ASSIGNMENTS_DOCTOR_LASTNAME_VALUE)
                                .append(ApplicationConstants.DEPARTMENT, ApplicationConstants.ASSIGNMENTS_DOCTOR_DEPARTMENT_VALUE)
                                .append(ApplicationConstants.DATE_OF_ADMISSION, ApplicationConstants.ASSIGNMENTS_DOCTOR_DATE_VALUE)).as(ApplicationConstants.DOCTORS)
        );
        AggregationResults<AssignedDoctorsToPatientDto> results = mongoTemplate.aggregate(aggregation, ApplicationConstants.PATIENT_COLLECTION, AssignedDoctorsToPatientDto.class);
        return results.getUniqueMappedResult();
    }
}
