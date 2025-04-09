package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DataNotFoundException extends Exception {
    private final UUID id;

    public DataNotFoundException(String message, UUID id) {
        super(message);
        this.id = id;
    }
public class DataNotFoundException  extends RuntimeException{
 public DataNotFoundException(String errorMessage){
     super(errorMessage);
 }
}
