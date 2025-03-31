package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ErrorConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.BaseResponse;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for retrieving doctor details based on name search.
 */
@Service
@Slf4j
public class DoctorRetrievalService {

    private final DoctorValidator doctorValidator;
    private final DoctorRepository doctorRepository;

    public DoctorRetrievalService(DoctorValidator doctorValidator, DoctorRepository doctorRepository) {
        this.doctorValidator = doctorValidator;
        this.doctorRepository = doctorRepository;
    }

    /**
     * Retrieves doctors by first name, last name, or both, with validation.
     */
    public BaseResponse retriveDoctorByName(String name) {
        BaseResponse baseResponse = new BaseResponse();
        List<String> errors = doctorValidator.validateDoctorName(name);
        if (!errors.isEmpty()) {
            baseResponse.setSuccess(false);
            baseResponse.setError(errors);
            return baseResponse;
        }
        List<DoctorEntity> doctorEntityList;
        String[] nameParts = name.trim().split("\\s+");

        if (nameParts.length == 1) {
            doctorEntityList = doctorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(nameParts[0], nameParts[0]);
        } else if (nameParts.length == 2) {

            doctorEntityList = doctorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(nameParts[0], nameParts[1]);
        } else {
            baseResponse.setSuccess(false);
            baseResponse.setError(List.of(ErrorConstants.NOT_EMPTY_ERROR_MESSAGE));
            return baseResponse;
        }
        DoctorDto doctorDto = new DoctorDto();
        List<DoctorDto> doctorData = new ArrayList<>();
        for (int i = 0; i < doctorEntityList.size(); i++) {
            doctorDto.setId(doctorEntityList.get(i).getId());
            doctorDto.setFirstName(doctorEntityList.get(i).getFirstName());
            doctorDto.setLastName(doctorEntityList.get(i).getLastName());
            doctorDto.setDepartment(doctorEntityList.get(i).getDepartment());
            doctorData.add(doctorDto);
        }
        baseResponse.setSuccess(true);
        baseResponse.setDoctors(doctorData);
        return baseResponse;
    }
}
