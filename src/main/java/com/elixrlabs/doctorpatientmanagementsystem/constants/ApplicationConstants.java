package com.elixrlabs.doctorpatientmanagementsystem.constants;

/**
 * This class contains API endpoint paths, request parameter names,
 * and commonly used symbols to ensure consistency and reduce hardcoded values
 * across the application.
 */
public class ApplicationConstants {
    public static final String ASSIGNMENTS = "assignments";
    public static final String ASSIGNMENT_COLLECTION = "tp_doctor_patient_assignment";
    public static final String ASSIGNMENTS_DOCTOR = "assignments.doctor";
    public static final String ASSIGNMENTS_DOCTOR_ID = "assignments.doctorId";
    public static final String COLON = " :";
    public static final String DOCTORS = "doctors";
    public static final String DOCTORS_COLLECTION = "tp_doctors";
    public static final String DOCTORS_NOT_FOUND = "No Doctors found";
    public static final String EMPTY_NAME_QUERY_PARAM = "Query parameter 'name' cannot be empty. Please provide either a first name or a last name to filter";
    public static final String EMPTY_SPACE = " ";
    public static final String FIRST_NAME = "firstName";
    public static final String FIRSTNAME = "/firstName";
    public static final String ID = "_id";
    public static final String INVALID_URL = "Invalid URL";
    public static final String LAST_NAME = "lastName";
    public static final String LASTNAME = "/lastName";
    public static final String NO_PATIENTS_FOUND = "No patients found";
    public static final String OPERATION = "op";
    public static final String PARAM_DOCTOR_NAME = "name";
    public static final String PATH = "path";
    public static final String PATIENT_COLLECTION = "tp_patients";
    public static final String PATCH_ADD_OPERATION = "add";
    public static final String PATCH_OPERATION_KEY = "op";
    public static final String PATCH_PATH_DEPARTMENT = "/department";
    public static final String PATCH_PATH_FIRST_NAME = "/firstName";
    public static final String PATCH_PATH_KEY = "path";
    public static final String PATCH_PATH_LAST_NAME = "/lastName";
    public static final String PATCH_REMOVE_OPERATION = "remove";
    public static final String PATCH_REPLACE_OPERATION = "replace";
    public static final String PATIENT_FIRSTNAME_ERROR = "Patient first name cannot be empty or null.";
    public static final String PATIENT_FIRSTNAME_PATTERN_ERROR = "Patient first name must contain only alphabet.";
    public static final String PATIENT_ID = "patientId";
    public static final String PATIENT_ID_NOT_FOUND = "Patient not found with Id : ";
    public static final String PATIENT_LASTNAME_ERROR = "Patient last name cannot be empty or null.";
    public static final String PATIENT_LASTNAME_PATTERN_ERROR = "Patient last name must contain only alphabet.";
    public static final String QUERY_PARAMS_CANNOT_NULL = "Query params cannot be null.";
    public static final String REGEX_ALPHABET_PATTERN = "^[a-zA-Z\\s]+$";
    public static final String REGEX_DEPARTMENTNAME_PATTERN = "^[a-zA-Z\\s\\-'.,]+$";
    public static final String REGEX_PATIENT_NAME_PATTERN = "^[a-zA-Z\\s]{1,50}$";
    public static final String REGEX_UUID_PATTERN = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";
    public static final String REPLACE = "replace";
    public static final String SINGLE_QUOTE = "'";
    public static final String VALUE = "value";
}
