package com.elixrlabs.doctorpatientmanagementsystem.constants;

/**
 * Class to store the constants used in test package
 */
public class TestApplicationConstants {
    public static final String APPLICATION_JSON = "application/json";
    public static final String BLANK_FIRST_NAME = " ";
    public static final String DATA_NOT_FOUND_EXCEPTION_NOT_THROWN_MESSAGE = "Expected DataNotFoundException,but not thrown";
    public static final String DEPARTMENT_NAME = "departmentName";
    public static final String DOCTOR_DELETE_ENDPOINT = "/doctor/{doctorId}";
    public static final String DOCTOR_ENDPOINT = "/doctors";
    public static final String DOCTORS_NOT_FOUND = "No Doctors found :'firstName'";
    public static final String EMPTY_NAME_QUERY_PARAM = "Query parameter 'name' cannot be empty. Please provide either a first name or a last name to filter";
    public static final String EMPTY_QUERY_STRING = " ";
    public static final String EXCEPTION_NOT_THROWN_MESSAGE = "Expected Invalid assignment data exception due to invalid doctor ID was not thrown";
    public static final String EXPECTED_DATA_NOT_FOUND_EXCEPTION = "Expected an error when the patient with the given ID was not found";
    public static final String EXPECTED_FIRST_NAME = "expectedName";
    public static final String EXPECTED_INVALID_USER_INPUT_EXCEPTION = "Expected InvalidUserInputException,but not thrown";
    public static final String EXPECTED_INVALID_UUID_EXCEPTION = "Expected InvalidUuidException,but not thrown";
    public static final String EXPECTED_PATIENT_ALREADY_ASSIGNED_EXCEPTION = "Expected an error because the patient is already assigned to a doctor";
    public static final String FIRST_NAME = "firstName";
    public static final String GET_DOCTOR_BY_ID = "/doctors/{id}";
    public static final String GET_PATIENT_BY_ID_END_POINT = "/patient/{id}";
    public static final String INVALID_FIRST_NAME = "First name invalid";
    public static final String INVALID_NAME = "InvalidName";
    public static final String INVALID_UUID = "12345";
    public static final String JSON_KEY_ERRORS = "$.errors";
    public static final String JSON_KEY_SUCCESS = "$.success";
    public static final String JSON_PATH_ERRORS_FIRST = "$.errors[0]";
    public static final String JSON_PATH_MESSAGES_FIRST = "$.messages[0]";
    public static final String LAST_NAME = "lastName";
    public static final String MOCK_EXCEPTION_MESSAGE = "Exception Message";
    public static final String NO_PATIENT_FOUND_WITH_ID = "No patient found with ID:";
    public static final String PATCH_CONTENT = "[{\"op\": \"replace\", \"path\": \"/firstName\", \"value\": \"expectedName\"}]";
    public static final String PATCH_CONTENT_TYPE = "application/json-patch+json";
    public static final String PATCH_INVALID_CONTENT = "[{\"op\": \"replace\", \"path\": \"/firstName\", \"value\": \"InvalidName\"}]";
    public static final String PATCH_PATIENT_ENDPOINT = "/patient/{patientId}";
    public static final String PATIENT_DELETED_SUCCESSFULLY = "Patient deleted successfully";
    public static final String PATIENT_ENDPOINT = "/patient/{patientId}";
    public static final String PATIENT_NOT_FOUND = "Patient not found";
    public static final String POST_PATIENTS_END_POINT = "/patients";
    public static final String QUERY_PARAM_NAME = "name";
    public static final String UUID = "e562f728-1911-4ac5-91f0-de67772b2384";
}
