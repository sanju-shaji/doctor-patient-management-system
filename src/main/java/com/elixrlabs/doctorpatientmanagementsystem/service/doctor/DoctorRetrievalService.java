package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.DoctorPatientConstants;
import com.elixrlabs.doctorpatientmanagementsystem.constants.ErrorConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for retrieving doctor details based on name search.
 */
@Service
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
    public ResponseEntity<DoctorListResponse> retrieveDoctorByName(String name) {
        DoctorListResponse doctorListResponse = new DoctorListResponse();
        boolean doctorValidatorResponse = doctorValidator.validateDoctorName(name);
        if (doctorValidatorResponse) {
            doctorListResponse.setSuccess(false);
            doctorListResponse.setError(ErrorConstants.NOT_EMPTY_ERROR_MESSAGE);
            return new ResponseEntity<>(doctorListResponse, HttpStatus.BAD_REQUEST);
        }
        List<DoctorEntity> doctorEntityList = doctorRepository.findByName(name);
        if (!doctorEntityList.isEmpty()) {
            List<DoctorDto> doctorsData = new ArrayList<>();
            for (DoctorEntity doctorEntity : doctorEntityList) {
                DoctorDto doctorDto = new DoctorDto();
                doctorDto.setId(doctorEntity.getId());
                doctorDto.setFirstName(doctorEntity.getFirstName());
                doctorDto.setLastName(doctorEntity.getLastName());
                doctorDto.setDepartment(doctorEntity.getDepartment());
                doctorsData.add(doctorDto);
            }
            doctorListResponse.setSuccess(true);
            doctorListResponse.setDoctors(doctorsData);
            return new ResponseEntity<>(doctorListResponse, HttpStatus.OK);
        } else {
            doctorListResponse.setSuccess(false);
            doctorListResponse.setError(ErrorConstants.DOCTOR_NOT_FOUND_ERROR_MESSAGE + DoctorPatientConstants.COLON + DoctorPatientConstants.SINGLE_QUOTE + name + DoctorPatientConstants.SINGLE_QUOTE);
            return new ResponseEntity<>(doctorListResponse, HttpStatus.NOT_FOUND);
        }
    }
}
