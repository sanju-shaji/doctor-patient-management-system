package com.elixrlabs.doctorpatientmanagementsystem.constants;

/**
 * This class represents the endpoint constants
 */
public class ApiConstants {
    public static final String ASSIGN_DOCTOR_PATIENT = "/assignDoctorToPatient";
    public static final String DOCTORS_END_POINT = "/doctors";
    public static final String GET_DOCTOR_BY_ID = "/doctors/{id}";
    public static final String GET_DOCTOR_BY_PATIENT_ID = "/assignedDoctorsByPatientId";
    public static final String GET_PATIENT_BY_ID_API = "patient/{id}";
    public static final String GET_PATIENT_BY_NAME_API = "/patient";
    public static final String GET_PATIENTS_BY_DOCTOR_ID = "/patientsByDoctorId";
    public static final String PATCH_DOCTOR_BY_ID = "/doctor/{doctorId}";
    public static final String PATIENT_API = "/patient";
    public static final String PATIENT_BY_ID = "patient/{id}";
    public static final String PATIENT_ID = "/{patientId}";
    public static final String UN_ASSIGN_DOCTOR_FROM_PATIENT="UnAssignDoctorFromPatient";
    public static final String PATIENTS_API = "/patients";
}
