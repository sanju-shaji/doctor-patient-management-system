package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service class for retrieving doctor details based on name search.
 */
@Service
public class DoctorRetrievalService {

    private final DoctorValidation doctorValidation;
    private final DoctorRepository doctorRepository;

    public DoctorRetrievalService(DoctorValidation doctorValidation, DoctorRepository doctorRepository) {
        this.doctorValidation = doctorValidation;
        this.doctorRepository = doctorRepository;
    }

    /**
     * Retrieves doctors by first doctorName, last doctorName, or both, with validation.
     */
    public ResponseEntity<DoctorListResponse> retrieveDoctorByName(String doctorName) {
        DoctorListResponse doctorListResponse = new DoctorListResponse();
        boolean doctorValidatorResponse = doctorValidation.validateDoctorName(doctorName);
        if (doctorValidatorResponse) {
            doctorListResponse.setSuccess(false);
            doctorListResponse.setErrors(Collections.singletonList((ApplicationConstants.EMPTY_NAME_QUERY_PARAM)));
            return new ResponseEntity<>(doctorListResponse, HttpStatus.BAD_REQUEST);
        }
        List<DoctorEntity> doctorEntityList = doctorRepository.findByName(doctorName);
        if (!doctorEntityList.isEmpty()) {
            List<DoctorDto> doctorsData = new ArrayList<>();
            for (DoctorEntity doctorEntity : doctorEntityList) {
                DoctorDto doctorDto = DoctorDto.builder()
                        .id(doctorEntity.getId())
                        .firstName(doctorEntity.getFirstName())
                        .lastName(doctorEntity.getLastName())
                        .department(doctorEntity.getDepartment()).build();
                doctorsData.add(doctorDto);
            }
            doctorListResponse.setSuccess(true);
            doctorListResponse.setDoctors(doctorsData);
            return new ResponseEntity<>(doctorListResponse, HttpStatus.OK);
        } else {
            doctorListResponse.setSuccess(false);
            doctorListResponse.setErrors(Collections.singletonList(ApplicationConstants.DOCTORS_NOT_FOUND + ApplicationConstants.COLON + ApplicationConstants.SINGLE_QUOTE + doctorName + ApplicationConstants.SINGLE_QUOTE));
            return new ResponseEntity<>(doctorListResponse, HttpStatus.NOT_FOUND);
        }
    }
}
