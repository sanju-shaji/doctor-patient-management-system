package com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PostResponse extends BaseResponse {
    private UUID id;
    private UUID doctorId;
    private UUID patientId;
    private Date dateOfAdmission;
}
