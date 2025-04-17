package com.elixrlabs.doctorpatientmanagementsystem.repository.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DataBaseConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.DoctorWithAssignedPatientsData;
import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

/**
 * Implementation of interface that performs custom aggregation queries to retrieve doctor-patient assignment data.
 */
@RequiredArgsConstructor
@Repository
public class DoctorWithAssignedPatientsDAOImpl implements DoctorWithAssignedPatientsDAO {
    private final MongoTemplate mongoTemplate;

    /**
     * Method that retrieves the details of a doctor and the list of patients assigned to them.
     * Matches the doctor by the given UUID.
     * Performs a lookup to join with the tp_doctor_patient_assignment collection.
     * Unwinds the assignments array to normalize data.
     * performs another lookup to join with the patients collection.
     * Unwinds the patient information.
     * Groups the result by doctorId and places assigned patients in a list.
     */
    @Override
    public DoctorWithAssignedPatientsData getAssignedPatientsByDoctorId(UUID id) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where(ApplicationConstants.ID).is(id)),
                Aggregation.lookup(DataBaseConstants.DOCTOR_PATIENT_ASSIGNMENT_COLLECTION_NAME, ApplicationConstants.ID, ApplicationConstants.DOCTOR_ID, ApplicationConstants.ASSIGNMENTS),
                unwind(ApplicationConstants.ASSIGNMENTS, true),
                Aggregation.lookup(DataBaseConstants.PATIENT_COLLECTION_NAME, ApplicationConstants.ASSIGNMENTS_PATIENT_ID, ApplicationConstants.ID, ApplicationConstants.ASSIGNMENTS_PATIENT),
                unwind(ApplicationConstants.ASSIGNMENTS_PATIENT, true),
                group(ApplicationConstants.ID)
                        .first(ApplicationConstants.FIRST_NAME).as(ApplicationConstants.FIRST_NAME)
                        .first(ApplicationConstants.LAST_NAME).as(ApplicationConstants.LAST_NAME)
                        .first(ApplicationConstants.DEPARTMENT).as(ApplicationConstants.DEPARTMENT)
                        .push(ApplicationConstants.ASSIGNMENTS_PATIENT).as(ApplicationConstants.PATIENTS)
                        .push(new Document().append(ApplicationConstants.ID,ApplicationConstants.ASSIGNMENTS_PATIENT_ID_VALUE)
                                .append(ApplicationConstants.FIRST_NAME, ApplicationConstants.ASSIGNMENTS_PATIENT_FIRSTNAME_VALUE)
                                .append(ApplicationConstants.LAST_NAME, ApplicationConstants.ASSIGNMENTS_PATIENT_LASTNAME_VALUE)
                                .append(ApplicationConstants.DATE_OF_ADMISSION, ApplicationConstants.ASSIGNMENTS_DATE_OF_ADMISSION_VALUE)).as(ApplicationConstants.PATIENTS)
        );
        AggregationResults<DoctorWithAssignedPatientsData> results = mongoTemplate.aggregate(aggregation, DataBaseConstants.DOCTOR_COLLECTION_NAME, DoctorWithAssignedPatientsData.class);
        return results.getUniqueMappedResult();
    }
}
