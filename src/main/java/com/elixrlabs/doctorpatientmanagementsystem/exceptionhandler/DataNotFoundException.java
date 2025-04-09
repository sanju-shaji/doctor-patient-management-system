package com.elixrlabs.doctorpatientmanagementsystem.exceptionhandler;

public class DataNotFoundException  extends RuntimeException{
 public DataNotFoundException(String errorMessage){
     super(errorMessage);
 }
}
