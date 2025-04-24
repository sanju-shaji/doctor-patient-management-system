package com.elixrlabs.doctorpatientmanagementsystem.util;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
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
}
