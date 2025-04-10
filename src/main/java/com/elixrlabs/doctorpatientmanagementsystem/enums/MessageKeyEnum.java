package com.elixrlabs.doctorpatientmanagementsystem.enums;

/**
 * enum representing  keys to retrieve messages from the messages.properties file.
 */
public enum MessageKeyEnum {
    BLANK_UUID("BLANK.UUID"),
    INVALID_UUID_FORMAT("INVALID.UUID.FORMAT"),
    NO_PATIENT_FOUND("NO.PATIENT.FOUND"),
    PATIENT_ASSIGNED_TO_DOCTOR("PATIENT.ASSIGNED.TO.DOCTOR"),
    PATIENT_DELETED_SUCCESSFULLY("PATIENT.DELETED.SUCCESSFULLY"),
    PATIENT_NOT_ASSIGNED("patient.not.assigned"),
    EMPTY_UUID("empty.uuid"),
    INVALID_UUID_ERROR("invalid.uuid.error"),
    USER_NOT_FOUND_ERROR("user.not.found.error"),
    EMPTY_FIRSTNAME("empty.first.name"),
    EMPTY_LASTNAME("empty.lastname"),
    FIRSTNAME_PATTERN_ERROR("firstname.pattern.error"),
    LASTNAME_PATTERN_ERROR("last.name.pattern.error"),
    EMPTY_DEPARTMENTNAME("empty.department.name"),
    DEPARTMENTNAME_PATTERN_ERROR("department.name.pattern.error"),
    PATIENT_NOT_FOUND_ERROR("patient.not.found.error"),
    SERVER_ERROR("server.error"),
    INVALID_REQUESTBODY_ERROR("invalid.request.body.error");
    private final String Key;

    MessageKeyEnum(String Key) {
        this.Key = Key;
    }

    @Override
    public String toString() {
        return Key;
    }
}
