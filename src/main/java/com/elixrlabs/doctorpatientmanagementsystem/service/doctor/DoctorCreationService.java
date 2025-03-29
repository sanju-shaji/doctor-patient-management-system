package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.PostDoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service Class for Doctor Module
 */
@Service
public class DoctorCreationService {
    @Autowired
    DoctorRepository doctorRepository;

    /**
     * Method which contains the business logic to post doctor details to database
     * @param postDoctorDto
     * @return
     */
    public PostDoctorDto createDoctor(PostDoctorDto postDoctorDto) {
        DoctorEntity doctorEntity = new DoctorEntity();
        doctorEntity.setId(UUID.randomUUID());
        doctorEntity.setFirstName(postDoctorDto.getFirstName().trim());
        doctorEntity.setLastName(postDoctorDto.getLastName().trim());
        doctorEntity.setDepartment(postDoctorDto.getDepartment());
        DoctorEntity createDoctor;
        DoctorValidation doctorValidation = new DoctorValidation();
        List<String> errorMessageList = doctorValidation.validatePostDoctor(doctorEntity);
        if (!errorMessageList.isEmpty()) {
            PostDoctorDto errorResponseDto = new PostDoctorDto();
            errorResponseDto.setSuccess(false);
            errorResponseDto.setError(errorMessageList);
            return errorResponseDto;
        }
        createDoctor = doctorRepository.save(doctorEntity);
        PostDoctorDto responseDto = new PostDoctorDto(createDoctor);
        responseDto.setSuccess(true);
        responseDto.setError(null);
        return responseDto;
    }
}
