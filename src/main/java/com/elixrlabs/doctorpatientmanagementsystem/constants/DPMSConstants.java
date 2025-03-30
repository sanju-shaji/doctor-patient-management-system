package com.elixrlabs.doctorpatientmanagementsystem.constants;

/**
 * class for storing all the string constants
 */
public class DPMSConstants {
    public static final String DEPARTMENTNAME_PATTERN_ERROR = "Invalid Department Name";
    public static final String EMPTY_DEPARTMENTNAME = "Department name should not be an empty";
    public static final String EMPTY_FIRSTNAME = "First name should not be an empty string";
    public static final String EMPTY_LASTNAME = "Last name should not be an empty string";
    public static final String FIRSTNAME_PATTERN_ERROR = "First name should only contain alphabets";
    public static final String GET_DOCTOR_BY_ID_API = "/GET/doctors/{id}";
    public static final String INVALID_UUID_ERROR = "Invalid Id. Please provide a valid UUID";
    public static final String LASTNAME_PATTERN_ERROR = "Last name should only contain alphabets";
    public static final String POST_DOCTOR_API = "POST/doctors";
    public static final String REGEX_ALPHABET_PATTERN = "^[a-zA-Z\\s]+$";
    public static final String REGEX_DEPARTMENTNAME_PATTERN = "^[a-zA-Z\\s\\-'.,]+$";
    public static final String USER_NOT_FOUND_ERROR = "User Not Found In Database";
}
