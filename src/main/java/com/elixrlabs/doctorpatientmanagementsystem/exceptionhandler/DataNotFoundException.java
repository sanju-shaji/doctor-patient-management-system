package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

import lombok.Getter;


@Getter
public class DataNotFoundException extends Exception {
    public DataNotFoundException(String message) {
        super(message);
    }
}
