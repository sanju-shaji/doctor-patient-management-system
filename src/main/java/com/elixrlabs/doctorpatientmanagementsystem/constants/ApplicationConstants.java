package com.elixrlabs.doctorpatientmanagementsystem.constants;

/**
 * This class contains API endpoint paths, request parameter names,
 * and commonly used symbols to ensure consistency and reduce hardcoded values
 * across the application.
 */
public class ApplicationConstants {
    public static final String ASSIGNMENTS = "assignments";
    public static final String ASSIGNMENTS_PATIENT = "assignments.patient";
    public static final String ASSIGNMENTS_PATIENT_ID = "assignments.patientId";
    public static final String COLON = " :";
    public static final String DEPARTMENT ="department";
    public static final String DEPARTMENTNAME_PATTERN_ERROR = "Department Name should not contain any special symbols or numbers.";
    public static final String DOCTOR_ID = "doctorId";
    public static final String DOCTORS_NOT_FOUND = "No Doctors found.";
    public static final String EMPTY_DEPARTMENTNAME = "Department name should not be an empty or null.";
    public static final String EMPTY_FIRSTNAME = "First name should not be an empty string or null.";
    public static final String EMPTY_LASTNAME = "Last name should not be an empty string or null.";
    public static final String EMPTY_NAME_QUERY_PARAM = "Query parameter 'name' cannot be empty. Please provide either a first name or a last name to filter.";
    public static final String EMPTY_UUID = "Please provide a UUID. ID is missing.";
    public static final String FIRST_NAME = "firstName";
    public static final String FIRSTNAME_PATTERN_ERROR = "First name should only contain alphabets.";
    public static final String ID = "_id";
    public static final String INVALID_REQUESTBODY_ERROR = "Invalid request body.";
    public static final String INVALID_UUID_ERROR = "Invalid Id. Please provide a valid UUID.";
    public static final String LAST_NAME = "lastName";
    public static final String LASTNAME_PATTERN_ERROR = "Last name should only contain alphabets.";
    public static final String NO_PATIENTS_FOUND = "No patients found.";
    public static final String PARAM_DOCTOR_NAME = "name";
    public static final String PATIENTS = "patients";
    public static final String QUERY_PARAMS_CANNOT_NULL = "Query params cannot be null.";
    public static final String REGEX_ALPHABET_PATTERN = "^[a-zA-Z\\s]+$";
    public static final String REGEX_DEPARTMENTNAME_PATTERN = "^[a-zA-Z\\s\\-'.,]+$";
    public static final String REGEX_PATIENT_NAME_PATTERN = "^[a-zA-Z\\s]{1,50}$";
    public static final String REGEX_UUID_PATTERN = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";
    public static final String SERVER_ERROR = "Server error - ";
    public static final String SINGLE_QUOTE = "'";
    public static final String USER_NOT_FOUND_ERROR = "User is not found in database for the given ID : ";
}
