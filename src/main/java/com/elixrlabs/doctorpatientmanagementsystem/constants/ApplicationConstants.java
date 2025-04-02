package com.elixrlabs.doctorpatientmanagementsystem.constants;

/**
 * class for storing all the string constants
 */
public class ApplicationConstants {
    public static final String DEPARTMENTNAME_PATTERN_ERROR = "Department Name should not contain any special symbols or numbers";
    public static final String EMPTY_DEPARTMENTNAME = "Department name should not be an empty or null";
    public static final String EMPTY_FIRSTNAME = "First name should not be an empty string or null";
    public static final String EMPTY_LASTNAME = "Last name should not be an empty string or null";
    public static final String FIRSTNAME_PATTERN_ERROR = "First name should only contain alphabets";
    public static final String LASTNAME_PATTERN_ERROR = "Last name should only contain alphabets";
    public static final String POST_DOCTOR_API = "/doctors";
    public static final String REGEX_ALPHABET_PATTERN = "^[a-zA-Z\\s]+$";
    public static final String REGEX_DEPARTMENTNAME_PATTERN = "^[a-zA-Z\\s\\-'.,]+$";
    public static final String SERVER_ERROR = "Server error - ";
}
