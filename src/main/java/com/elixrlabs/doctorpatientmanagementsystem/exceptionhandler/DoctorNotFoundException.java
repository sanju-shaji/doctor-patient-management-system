package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DoctorNotFoundException extends Exception {
    private final UUID id;

    public DoctorNotFoundException(String message, UUID id) {
        super(message);
        this.id = id;
    }
}
