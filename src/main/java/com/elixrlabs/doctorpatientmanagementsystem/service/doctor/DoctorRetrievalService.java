package com.elixrlabs.doctorpatientmanagementsystem.service.doctor;

import com.elixrlabs.doctorpatientmanagementsystem.constants.ApplicationConstants;
import com.elixrlabs.doctorpatientmanagementsystem.dto.doctor.DoctorDto;
import com.elixrlabs.doctorpatientmanagementsystem.model.doctor.DoctorEntity;
import com.elixrlabs.doctorpatientmanagementsystem.repository.doctor.DoctorRepository;
import com.elixrlabs.doctorpatientmanagementsystem.validation.doctor.DoctorValidation;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * service class for implementing business logic for retrieving data from database
 */
@Service
public class DoctorRetrievalService {
    @Autowired
    DoctorRepository docRepo;
    @Autowired
    DoctorValidation doctorValidation;

    /**
     * Method which handles the logic to retrieve doctor data by using ID
     */
    public ResponseEntity<DoctorDto> getDoctorsById(String id) {
        if (StringUtils.isBlank(id)) {
            DoctorDto responseDto = DoctorDto.builder().success(false).errors(List.of(ApplicationConstants.EMPTY_UUID))
                    .build();
            return ResponseEntity.status(400).body(responseDto);
        }
        if (!doctorValidation.isValidUUID(id)) {
            DoctorDto responseDto = DoctorDto.builder().success(false)
                    .errors(List.of(ApplicationConstants.INVALID_UUID_ERROR)).build();
            return ResponseEntity.status(400).body(responseDto);
        }
        UUID Uuid = UUID.fromString(id);
        Optional<DoctorEntity> doctorEntity = docRepo.findById(Uuid);
        if (doctorEntity.isPresent()) {
            DoctorDto responseDto = DoctorDto.builder()
                            .id(doctorEntity.get().getId())
                    .firstName(doctorEntity.get().getFirstName())
                            .lastName(doctorEntity.get().getLastName())
                                    .department(doctorEntity.get().getDepartment())
                                            .success(true).build();
            return ResponseEntity.ok().body(responseDto);
        }
        List<String> invalidUUID = new ArrayList<>();
        invalidUUID.add(ApplicationConstants.USER_NOT_FOUND_ERROR);
        DoctorDto responseDto = DoctorDto.builder().success(false).errors(invalidUUID).build();

        return ResponseEntity.status(404).body(responseDto);
    }
}
