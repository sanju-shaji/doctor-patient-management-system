package com.elixrlabs.doctorpatientmanagementsystem.dto.doctorpatientassignment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;
@Getter
@Setter
@Builder
public class PostDoctorPatientAssignmentDto {
    @Id
    private UUID id;
    private UUID doctorId;
    private UUID patientId;
}
