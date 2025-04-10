package com.elixrlabs.doctorpatientmanagementsystem.enums;

/**
 * enum representing  keys to retrieve messages from the messages.properties file.
 */
public enum MessageKeyEnum {
    BLANK_UUID("BLANK.UUID"),
    INVALID_UUID_FORMAT("INVALID.UUID.FORMAT"),
    NO_PATIENT_FOUND("NO.PATIENT.FOUND"),
    PATIENT_ASSIGNED_TO_DOCTOR("PATIENT.ASSIGNED.TO.DOCTOR"),
    PATIENT_DELETED_SUCCESSFULLY("PATIENT.DELETED.SUCCESSFULLY");

    private final String Key;

    MessageKeyEnum(String Key) {
        this.Key = Key;
    }

    @Override
    public String toString() {
        return Key;
    }
}
