package com.elixrlabs.doctorpatientmanagementsystem.constants;

public class ApplicationConstants {
    public static final String PATIENT_FIRSTNAME_ERROR = "Patient first name cannot be empty or null.";
    public static final String PATIENT_LASTNAME_ERROR = "Patient last name cannot be empty or null.";
    public static final String PATIENT_FIRSTNAME_PATTERN_ERROR = "Patient first name must contain only alphabet.";
    public static final String PATIENT_LASTNAME_PATTERN_ERROR = "Patient last name must contain only alphabet.";
    public static final String PATIENTS_API = "/patients";
    public static final String REGEX_PATIENT_NAME_PATTERN = "^[a-zA-Z\\s]{1,50}$";
}
