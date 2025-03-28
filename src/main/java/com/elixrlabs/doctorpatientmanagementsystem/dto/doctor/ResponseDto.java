package com.elixrlabs.doctorpatientmanagementsystem.dto.doctor;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Base DTO for doctor class for setting success and error
 */
@Getter
@Setter
public class ResponseDto {
    public boolean success;
    public List<String> error;
}
