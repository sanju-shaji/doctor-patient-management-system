package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.DataNotFoundException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.EmptyUuidException;
import com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler.InvalidUuidExcetion;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorListResponse;
import com.elixrlabs.doctorpatientmanagementsystem.response.doctor.DoctorResponse;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

/**
 * Service class for retrieving doctor details based on name search.
 */
@Service
@RequiredArgsConstructor
public class DoctorRetrievalService {
    private final DoctorValidation doctorValidation;
    private final DoctorRepository doctorRepository;

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
            doctorListResponse.setErrors(Collections.singletonList(ApplicationConstants.DOCTORS_NOT_FOUND_ERROR + ApplicationConstants.COLON + ApplicationConstants.SINGLE_QUOTE + doctorName + ApplicationConstants.SINGLE_QUOTE));
            return new ResponseEntity<>(doctorListResponse, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Method to retrieve single doctor data using id
     *
     * @param id-uuid
     * @return Doctor response object with a single doctor data
     */
    public ResponseEntity<DoctorResponse> getDoctorsById(String id) throws Exception {
        if (StringUtils.isBlank(id)) {
            throw new EmptyUuidException(ApplicationConstants.EMPTY_UUID);
        }
        if (!doctorValidation.isValidUUID(id)) {
            throw new InvalidUuidExcetion(ApplicationConstants.INVALID_UUID_ERROR);
        }
        UUID uuid = UUID.fromString(id);
        Optional<DoctorEntity> doctorEntity = doctorRepository.findById(uuid);
        if (doctorEntity.isPresent()) {
            DoctorResponse responseDto = DoctorResponse.builder().id(doctorEntity.get().getId())
                    .firstName(doctorEntity.get().getFirstName())
                    .lastName(doctorEntity.get().getLastName())
                    .department(doctorEntity.get().getDepartment())
                    .success(true).build();
            return ResponseEntity.ok().body(responseDto);
        }
        throw new DataNotFoundException(ApplicationConstants.DOCTORS_NOT_FOUND_ERROR, uuid);
    }
}
