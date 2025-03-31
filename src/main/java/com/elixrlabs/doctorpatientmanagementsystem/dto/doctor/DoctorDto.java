package com.elixrlabs.doctorpatientmanagementsystem.dto.doctor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import java.util.UUID;

/** DTO representing doctor details for data transfer. */
@Getter
@Setter
@NoArgsConstructor
public class DoctorDto {
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private String department;
}
