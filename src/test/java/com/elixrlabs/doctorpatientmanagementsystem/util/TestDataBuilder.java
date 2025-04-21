package com.elixrlabs.doctorpatientmanagementsystem.util;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;

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
        return DoctorEntity.builder()
                .id(UUID.randomUUID())
                .firstName(doctorDtoBuilder().getFirstName())
                .lastName(doctorDtoBuilder().getLastName())
                .department(doctorDtoBuilder().getDepartment())
                .build();
    }

    /**
     * This method initializes a doctor response object so that it can be reused for doctor module testing
     *
     * @return doctor response object
     */
    public DoctorResponse doctorResponseBuilder() {
        return DoctorResponse.builder()
                .firstName(doctorEntityBuilder().getFirstName())
                .lastName(doctorEntityBuilder().getLastName())
                .department(doctorEntityBuilder().getDepartment())
                .success(true)
                .build();
    }
}
