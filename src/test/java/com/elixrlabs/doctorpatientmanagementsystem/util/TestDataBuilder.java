package com.elixrlabs.doctorpatientmanagementsystem.util;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment.AssignedDoctorsToPatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctorpatientassignment.DoctorPatientAssignmentModel;
import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.AssignedDoctorData;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment.DoctorPatientAssignmentResponse;

import java.util.ArrayList;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Data builder class for initializing data objects
 */
public class TestDataBuilder {
    /**
     * This method initializes a doctor dto object so that it can be reused for doctor module testing
     *
     * @return doctor dto
     */
    public DoctorDto doctorDtoBuilder() {
        return DoctorDto.builder()
                .id(UUID.fromString(TestApplicationConstants.UUID))
                .firstName(TestApplicationConstants.FIRST_NAME)
                .lastName(TestApplicationConstants.LAST_NAME)
                .department(TestApplicationConstants.DEPARTMENT_NAME)
                .build();
    }

    /**
     * This method initializes a doctor entity object so that it can be reused for doctor module testing
     *
     * @return doctor entity
     */
    public DoctorEntity doctorEntityBuilder() {
        DoctorDto doctorDto = doctorDtoBuilder();
        return DoctorEntity.builder()
                .id(UUID.fromString(TestApplicationConstants.UUID))
                .firstName(doctorDto.getFirstName())
                .lastName(doctorDto.getLastName())
                .department(doctorDto.getDepartment())
                .build();
    }

    /**
     * This method initializes a doctor response object so that it can be reused for doctor module testing
     *
     * @return doctor response object
     */
    public DoctorResponse doctorResponseBuilder() {
        DoctorEntity doctorEntity = doctorEntityBuilder();
        return DoctorResponse.builder()
                .id(doctorEntity.getId())
                .firstName(doctorEntity.getFirstName())
                .lastName(doctorEntity.getLastName())
                .department(doctorEntity.getDepartment())
                .success(true)
                .build();
    }

    /**
     * This method initializes invalid doctor response object so that it can be reused for doctor module testing
     *
     * @return doctor response object
     */
    public DoctorResponse invalidDoctorResponseBuilder() {
        return DoctorResponse.builder()
                .success(false)
                .errors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE))
                .build();
    }

    /**
     * Returns a list containing one sample DoctorDto for test data.
     *
     * @return a list with one DoctorDto object
     */
    public List<DoctorDto> doctorDtoListBuilder() {
        List<DoctorDto> doctorDtoList = new ArrayList<>();
        doctorDtoList.add(doctorDtoBuilder());
        return doctorDtoList;
    }

    /**
     * Returns a list containing one sample DoctorEntity for test data.
     *
     * @return a list with one DoctorEntity object
     */
    public List<DoctorEntity> doctorEntityListBuilder() {
        List<DoctorEntity> doctorEntityList = new ArrayList<>();
        doctorEntityList.add(doctorEntityBuilder());
        return doctorEntityList;
    }

    /**
     * Builds a successful response with a list of doctors and returns HTTP 200 OK.
     */
    public ResponseEntity<DoctorListResponse> buildDoctorListSuccessResponse(List<DoctorDto> doctors) {
        DoctorListResponse doctorListResponse = new DoctorListResponse();
        doctorListResponse.setDoctors(doctors);
        doctorListResponse.setSuccess(true);
        return ResponseEntity.ok().body(doctorListResponse);
    }

    /**
     * Builds an error response with a given error message and HTTP status.
     */
    public ResponseEntity<DoctorListResponse> buildDoctorListErrorResponse(HttpStatus statusCode) {
        DoctorListResponse doctorListResponse = new DoctorListResponse();
        doctorListResponse.setSuccess(false);
        doctorListResponse.setErrors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE));
        return ResponseEntity.status(statusCode).body(doctorListResponse);
    }

    public List<DoctorPatientAssignmentModel> doctorPatientAssignmentModelBuilder() {
        List<DoctorPatientAssignmentModel> response = new ArrayList<>();
        DoctorPatientAssignmentModel doctorPatientAssignmentModel = DoctorPatientAssignmentModel.builder()
                .id(UUID.randomUUID())
                .doctorId(doctorEntityBuilder().getId())
                .patientId(UUID.randomUUID())
                .dateOfAdmission(Date.from(Instant.now()))
                .isUnAssigned(false)
                .build();
        response.add(doctorPatientAssignmentModel);
        return response;
    }

    public ResponseEntity<BaseResponse> buildSuccessDeleteResponse(List<String> messages) {
        BaseResponse baseResponse = BaseResponse.builder()
                .success(true)
                .messages(messages)
                .build();
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    public ResponseEntity<BaseResponse> buildFailureDeleteResponse(List<String> errors, HttpStatus statusCode) {
        BaseResponse baseResponse = BaseResponse.builder()
                .success(false)
                .errors(errors)
                .build();
        return new ResponseEntity<>(baseResponse, statusCode);
    }


    /**
     * This method initializes a patient dto object so that it can be reused for doctor module testing
     *
     * @return patient dto
     */
    public PatientDto patientDtoBuilder() {
        return PatientDto.builder()
                .id(UUID.fromString(TestApplicationConstants.UUID))
                .firstName(TestApplicationConstants.FIRST_NAME)
                .lastName(TestApplicationConstants.LAST_NAME)
                .build();
    }

    /**
     * This method initializes a patient Model object so that it can be reused for doctor module testing
     *
     * @return doctor entity
     */
    public PatientModel patientModelBuilder() {
        return PatientModel.builder()
                .id(UUID.fromString(TestApplicationConstants.UUID))
                .firstName(patientDtoBuilder().getFirstName())
                .lastName(patientDtoBuilder().getLastName())
                .build();
    }

    /**
     * This method initializes a patient response object so that it can be reused for patient module testing
     *
     * @return patient response object
     */
    public PatientResponse patientResponseBuilder() {
        return PatientResponse.builder()
                .success(true)
                .data(patientDtoBuilder())
                .build();
    }

    /**
     * This method initializes invalid patient response object so that it can be reused for patient module testing
     *
     * @return patient response object
     */
    public PatientResponse invalidPatientResponseBuilder() {
        return PatientResponse.builder()
                .success(false)
                .errors(List.of(TestApplicationConstants.MOCK_EXCEPTION_MESSAGE))
                .build();
    }

    /**
     * This method initializes a AssignedDoctorData which is used in AssignedDoctorsToPatientDto object which is used in AssignedDoctorsToPatientDto so that it can be reused for doctor module testing
     *
     * @return doctor entity
     */
    public AssignedDoctorData assignedDoctorDataResponseBuilder() {
        return AssignedDoctorData.builder()
                .id(doctorEntityBuilder().getId())
                .firstName(doctorEntityBuilder().getFirstName())
                .lastName(doctorEntityBuilder().getLastName())
                .department(doctorEntityBuilder().getDepartment())
                .dateOfAdmission(Date.from(Instant.now()))
                .build();
    }

    /**
     * This method initializes a AssignedDoctorsToPatientDto object so that it can be reused for doctor module testing
     *
     * @return doctor entity
     */
    public AssignedDoctorsToPatientDto assignmentsToPatientDtoBuilder() {
        return AssignedDoctorsToPatientDto.builder()
                .id(doctorEntityBuilder().getId().toString())
                .firstName(doctorEntityBuilder().getFirstName())
                .lastName(doctorEntityBuilder().getLastName())
                .doctors(List.of(assignedDoctorDataResponseBuilder()))
                .build();
    }

    /**
     * This method initializes a valid DoctorPatientAssignmentResponse object so that it can be reused for doctor module testing
     *
     * @return DoctorPatientAssignmentResponse
     */
    public DoctorPatientAssignmentResponse assignmentResponseBuilder() {
        return DoctorPatientAssignmentResponse.builder()
                .id(doctorEntityBuilder().getId())
                .firstName(doctorEntityBuilder().getFirstName())
                .lastName(doctorEntityBuilder().getLastName())
                .doctors(List.of(assignedDoctorDataResponseBuilder()))
                .success(true)
                .build();
    }
}
