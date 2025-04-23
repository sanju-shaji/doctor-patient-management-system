package com.elixrlabs.doctorpatientmanagementsystem.util.patient;

import com.elixrlabs.doctorpatientmanagementsystem.dto.patient.PatientDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.patient.PatientModel;
import com.elixrlabs.doctorpatientmanagementsystem.response.patient.PatientResponse;

import java.util.UUID;
public class TestDataBuilder {
    public PatientDto patientDtoBuilder(){
        return PatientDto.builder()
                .id(UUID.fromString("12e3c0f9-6a09-4591-886c-3c9206798d95"))
                .firstName("Susmitha")
                .lastName("Mereddy")
                .build();
    }
    public PatientModel patientModelBuilder(){
        return PatientModel.builder()
                .id(patientDtoBuilder().getId())
                .firstName(patientDtoBuilder().getFirstName())
                .lastName(patientDtoBuilder().getLastName())
                .build();
    }
    public PatientResponse patientResponseBuilder(){
        return PatientResponse.builder()
                .success(true)
                .data(patientDtoBuilder())
                .build();
    }
}
