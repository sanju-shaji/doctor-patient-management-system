package com.elixrlabs.doctorpatientmanagementsystem.util;

import com.elixrlabs.doctorpatientmanagementsystem.constants.TestApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;

import java.util.ArrayList;
import java.util.List;

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
        DoctorDto doctorDto = doctorDtoBuilder();
        return DoctorEntity.builder()
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
}
