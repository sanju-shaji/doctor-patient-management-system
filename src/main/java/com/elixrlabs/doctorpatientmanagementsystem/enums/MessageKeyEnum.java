package com.elixrlabs.doctorpatientmanagementsystem.enums;

import lombok.Getter;

/**
 * enum representing  keys to retrieve messages from the messages.properties file.
 */
@Getter
public enum MessageKeyEnum {
    INVALID_UUID_FORMAT("invalid.uuid.format"),
    NO_PATIENT_FOUND("no.patient.found"),
    PATIENT_ASSIGNED_TO_DOCTOR("patient.assigned.to.doctor"),
    PATIENT_DELETED_SUCCESSFULLY("patient.deleted.successfully"),
    PATIENT_NOT_ASSIGNED("patient.not.assigned"),
    EMPTY_UUID("empty.uuid"),
    INVALID_UUID_ERROR("invalid.uuid.error"),
    USER_NOT_FOUND_ERROR("user.not.found.error"),
    EMPTY_FIRSTNAME("empty.first.name"),
    EMPTY_LASTNAME("empty.lastname"),
    FIRSTNAME_PATTERN_ERROR("firstname.pattern.error"),
    LASTNAME_PATTERN_ERROR("last.name.pattern.error"),
    EMPTY_DEPARTMENT_NAME("empty.department.name"),
    DEPARTMENT_NAME_PATTERN_ERROR("department.name.pattern.error"),
    PATIENT_NOT_FOUND_ERROR("patient.not.found.error"),
    SERVER_ERROR("server.error"),
    INVALID_REQUEST_BODY_ERROR("invalid.request.body.error"),
    REPLACE_NON_EXISTENT_FIELD_NOT_ALLOWED("replace.non.existent.field.not.allowed"),
    ADD_OPERATION_NOT_ALLOWED("add.operation.not.allowed"),
    REMOVE_OPERATION_NOT_ALLOWED("remove.operation.not.allowed"),
    MISSING_ID("missing.id");
    private final String key;

    MessageKeyEnum(String Key) {
        this.key = Key;
    }
}
