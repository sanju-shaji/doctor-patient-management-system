package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

import lombok.Getter;

@Getter
public class DoctorAlreadyUnassignedException extends Exception{
    public DoctorAlreadyUnassignedException(String message){
        super(message);
    }
}
