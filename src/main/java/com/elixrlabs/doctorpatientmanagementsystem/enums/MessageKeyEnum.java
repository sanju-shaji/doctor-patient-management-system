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
    PATIENT_DELETED_SUCCESSFULLY("patient.deleted.successfully"),
    PATIENT_ID_NOT_FOUND("patient.id.not.found"),
    DOCTOR_NOT_FOUND_ERROR("doctor.not.found.error"),
    DOCTOR_NOT_ASSIGNED("doctor.not.assigned"),
    PATIENT_FIRSTNAME_ERROR("patient.firstname.error"),
    PATIENT_FIRSTNAME_PATTERN_ERROR("patient.firstname.pattern.error"),
    PATIENT_LASTNAME_ERROR("patient.lastname.error"),
    PATIENT_LASTNAME_PATTERN_ERROR("patient.lastname.pattern.error"),
    ;
    private final String key;

    MessageKeyEnum(String Key) {
        this.key = Key;
    }
}
