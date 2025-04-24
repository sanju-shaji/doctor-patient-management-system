package com.elixrlabs.doctorpatientmanagementsystem.util;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;

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
        DoctorDto doctorDto=doctorDtoBuilder();
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
        DoctorEntity doctorEntity= doctorEntityBuilder();
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
}
