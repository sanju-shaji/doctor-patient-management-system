package com.elixrlabs.doctorpatientmanagementsystem.constants;

/**
 * This class contains API endpoint paths, request parameter names,
 * and commonly used symbols to ensure consistency and reduce hardcoded values
 * across the application.
 */
public class ApplicationConstants {
    public static final String ASSIGNMENT_COLLECTION = "tp_doctor_patient_assignment";
    public static final String ASSIGNMENTS = "assignments";
    public static final String ASSIGNMENTS_DATE_OF_ADMISSION_VALUE = "$assignments.dateOfAdmission";
    public static final String ASSIGNMENTS_DOCTOR = "assignments.doctor";
    public static final String ASSIGNMENTS_DOCTOR_DATE_VALUE = "$assignments.dateOfAdmission";
    public static final String ASSIGNMENTS_DOCTOR_DEPARTMENT_VALUE = "$assignments.doctor.department";
    public static final String ASSIGNMENTS_DOCTOR_FIRSTNAME_VALUE = "$assignments.doctor.firstName";
    public static final String ASSIGNMENTS_DOCTOR_ID = "assignments.doctorId";
    public static final String ASSIGNMENTS_DOCTOR_ID_VALUE = "$assignments.doctorId";
    public static final String ASSIGNMENTS_DOCTOR_LASTNAME_VALUE = "$assignments.doctor.lastName";
    public static final String ASSIGNMENTS_IS_UNASSIGNED = "assignments.isUnAssigned";
    public static final String ASSIGNMENTS_PATIENT = "assignments.patient";
    public static final String ASSIGNMENTS_PATIENT_FIRSTNAME_VALUE = "$assignments.patient.firstName";
    public static final String ASSIGNMENTS_PATIENT_ID = "assignments.patientId";
    public static final String ASSIGNMENTS_PATIENT_ID_VALUE = "$assignments.patientId";
    public static final String ASSIGNMENTS_PATIENT_LASTNAME_VALUE = "$assignments.patient.lastName";
    public static final String COLON = " :";
    public static final String DATE_OF_ADMISSION = "dateOfAdmission";
    public static final String DEPARTMENT = "department";
    public static final String DOCTOR_ID = "doctorId";
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
    public static final String PATIENT_ID = "patientId";
    public static final String PATCH_ADD_OPERATION = "add";
    public static final String PATCH_OPERATION_KEY = "op";
    public static final String PATCH_PATH_DEPARTMENT = "/department";
    public static final String PATCH_PATH_FIRST_NAME = "/firstName";
    public static final String PATCH_PATH_KEY = "path";
    public static final String PATCH_PATH_LAST_NAME = "/lastName";
    public static final String PATCH_REMOVE_OPERATION = "remove";
    public static final String PATCH_REPLACE_OPERATION = "replace";
    public static final String PATIENTS = "patients";
    public static final String QUERY_PARAMS_CANNOT_NULL = "Query params cannot be null.";
    public static final String REGEX_ALPHABET_PATTERN = "^[a-zA-Z\\s]+$";
    public static final String REGEX_DEPARTMENT_NAME_PATTERN = "^[a-zA-Z\\s\\-'.,]+$";
    public static final String REGEX_PATIENT_NAME_PATTERN = "^[a-zA-Z\\s]{1,50}$";
    public static final String REGEX_UUID_PATTERN = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";
    public static final String REPLACE = "replace";
    public static final String SINGLE_QUOTE = "'";
    public static final String VALUE = "value";
}
