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
 * Implementation of interface that performs custom aggregation queries to retrieve doctor-patient assignment data.
 */
@RequiredArgsConstructor
@Repository
public class DoctorPatientDAOImpl implements DoctorPatientDAO {
private final MongoTemplate mongoTemplate;

    /**
     * Method that retrieves the details of a Patient and the list of doctors assigned to them.
     * Matches the patient by the given UUID.
     * Performs a lookup to join with the tp_doctor_patient_assignment collection.
     * Unwinds the assignments array to normalize data.
     * performs another lookup to join with the doctors' collection.
     * Unwinds the doctors information.
     * Groups the result by patient id and places assigned doctors in a list.
     * @param patientId-patientId
     * @return Patient data with list of assigned doctors
     */
    @Override
    public AssignedDoctorsToPatientDto getAssignedDoctorsByPatientId(UUID patientId) {
        Aggregation aggregation = newAggregation(
                Aggregation.match(Criteria.where(ApplicationConstants.ID).is(patientId)),
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
