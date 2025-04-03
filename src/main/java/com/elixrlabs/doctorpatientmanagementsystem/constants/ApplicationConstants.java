package com.elixrlabs.doctorpatientmanagementsystem.constants;

/**
 * This class contains API endpoint paths, request parameter names,
 * and commonly used symbols to ensure consistency and reduce hardcoded values
 * across the application.
 */
public class ApplicationConstants {
    public static final String DOCTORS_END_POINT = "/doctors";
    public static final String PARAM_DOCTOR_NAME = "name";
    public static final String COLON = " :";
    public static final String SINGLE_QUOTE = "'";
    public static final String EMPTY_NAME_QUERY_PARAM = "Query parameter 'name' cannot be empty. Please provide either a first name or a last name to filter";
    public static final String DOCTORS_NOT_FOUND = "No Doctors found";
}
