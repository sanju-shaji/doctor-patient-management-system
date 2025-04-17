package com.elixrlabs.doctorpatientmanagementsystem.response.doctorpatientassignment;

import com.elixrlabs.doctorpatientmanagementsystem.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;
/**
 * Response object of individual doctor which is to be added to the list
 */
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
