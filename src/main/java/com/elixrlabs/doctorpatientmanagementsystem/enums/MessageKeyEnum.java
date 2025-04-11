package com.elixrlabs.doctorpatientmanagementsystem.enums;

import lombok.Getter;

/**
 * enum representing  keys to retrieve messages from the messages.properties file.
 */
@Getter
public enum MessageKeyEnum {
    BLANK_UUID("blank.uuid"),
    INVALID_UUID_FORMAT("invalid.uuid.format"),
    NO_PATIENT_FOUND("no.patient.found"),
    PATIENT_ASSIGNED_TO_DOCTOR("patient.assigned.to.doctor"),
    PATIENT_DELETED_SUCCESSFULLY("patient.deleted.successfully");
    private final String key;

    MessageKeyEnum(String Key) {
        this.key = Key;
    }
}
