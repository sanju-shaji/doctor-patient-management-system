package com.elixrlabs.doctorpatientmanagementsystem.constants;

/**
 * This class represents constants realted to database
 */
public class DataBaseConstants {
    public static final String DOCTOR_COLLECTION_NAME = "tp_doctors";
    public static final String PATIENT_COLLECTION_NAME = "tp_patients";
    public static final String FETCH_DOCTOR_BY_FIRSTNAME_OR_LASTNAME_QUERY = "{ $or: [ { 'firstName': { $regex: ?0, $options: 'i' } }, { 'lastName': { $regex: ?0, $options: 'i' } } ] }";
}
