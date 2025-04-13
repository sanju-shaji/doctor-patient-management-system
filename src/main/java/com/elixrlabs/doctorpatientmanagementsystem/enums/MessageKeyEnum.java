package com.elixrlabs.doctorpatientmanagementsystem.enums;

import lombok.Getter;

/**
 * enum representing  keys to retrieve messages from the messages.properties file.
 */
@Getter
public enum MessageKeyEnum {
    INVALID_UUID_FORMAT("invalid.uuid.format"),
    NO_PATIENT_FOUND("no.patient.found"),
    NO_DOCTOR_FOUND("no.doctor.found"),
    PATIENT_ASSIGNED_TO_DOCTOR("patient.assigned.to.doctor"),
    PATIENT_DELETED_SUCCESSFULLY("patient.deleted.successfully"),
    REPLACE_NON_EXISTENT_FIELD_NOT_ALLOWED("replace.non.existent.field.not.allowed"),
    ADD_OPERATION_NOT_ALLOWED("add.operation.not.allowed"),
    REMOVE_OPERATION_NOT_ALLOWED("remove.operation.not.allowed"),
    MISSING_ID("missing.id"),
    EMPTY_FIRSTNAME("empty.first.name"),
    EMPTY_LASTNAME("empty.lastname"),
    EMPTY_DEPARTMENT("empty.department.name"),
    FIRSTNAME_PATTERN_ERROR("firstname.pattern.error"),
    LASTNAME_PATTERN_ERROR("last.name.pattern.error"),
    DEPARTMENT_PATTERN_ERROR("department.name.pattern.error"),
    SERVER_ERROR("server.error"),
    DELETE_DOCTOR_SUCCESSFULLY("doctor.deleted.successfully");
    private final String key;

    MessageKeyEnum(String Key) {
        this.key = Key;
    }
}
