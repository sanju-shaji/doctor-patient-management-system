package com.elixrlabs.doctorpatientmanagementsystem.constants;


/**
 * Class to store the constants used in test package
 */
public class TestApplicationConstants {
    public static final String DEPARTMENT_NAME = "departmentName";
    public static final String EXPECTED_DATA_NOT_FOUND_EXCEPTION = "Expected an error when the patient with the given ID was not found";
    public static final String EXPECTED_FIRST_NAME = "expectedName";
    public static final String FIRST_NAME = "firstName";
    public static final String INVALID_FIRST_NAME = "First name invalid";
    public static final String INVALID_NAME = "InvalidName";
    public static final String INVALID_UUID = "12345";
    public static final String LAST_NAME = "lastName";
    public static final String MOCK_EXCEPTION_MESSAGE = "Exception Message";
    public static final String PATCH_CONTENT = "[{\"op\": \"replace\", \"path\": \"/firstName\", \"value\": \"expectedName\"}]";
    public static final String PATCH_CONTENT_TYPE = "application/json-patch+json";
    public static final String PATCH_INVALID_CONTENT = "[{\"op\": \"replace\", \"path\": \"/firstName\", \"value\": \"InvalidName\"}]";
    public static final String PATCH_PATIENT_ENDPOINT = "/patient/{patientId}";
    public static final String PATIENT_NOT_FOUND = "Patient not found";
    public static final String PATIENT_UPDATED_SUCCESSFULLY = "Patient updated successfully";
    public static final String UUID = "e562f728-1911-4ac5-91f0-de67772b2384";
}