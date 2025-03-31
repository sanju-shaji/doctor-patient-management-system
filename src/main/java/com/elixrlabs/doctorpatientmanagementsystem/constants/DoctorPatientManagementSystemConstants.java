package com.elixrlabs.doctorpatientmanagementsystem.constants;

public class DoctorPatientManagementSystemConstants {
    public static final String EMPTY_PATIENT_FIRSTNAME_ERROR = "Patient first name cannot be empty";
    public static final String EMPTY_PATIENT_LASTNAME_ERROR = "Patient last name cannot be empty.";
    public static final String PATIENT_FIRSTNAME_PATTERN_ERROR = "Patient first name must contain only alphabets.";
    public static final String PATIENT_LASTNAME_PATTERN_ERROR = "Patient last name must contain only alphabets.";
    public static final String PATIENT_SAVE_ERROR = "An error occurred while saving the patient. Please try again.";
    public static final String POST_PATIENTS_API = "/patients";
    public static final String REGEX_PATIENT_NAME_PATTERN = "^[a-zA-Z]{1,50}$";
}
