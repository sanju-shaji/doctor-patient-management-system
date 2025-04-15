package com.elixrlabs.doctorpatientmanagementsystem.enums;

import lombok.Getter;

/**
 * enum representing  keys to retrieve messages from the messages.properties file.
 */
@Getter
public enum MessageKeyEnum {
    ADD_OPERATION_NOT_ALLOWED("add.operation.not.allowed"),
    BLANK_UUID("blank.uuid"),
    DELETE_DOCTOR_SUCCESSFULLY("doctor.deleted.successfully"),
    DEPARTMENT_NAME_PATTERN_ERROR("department.name.pattern.error"),
    EMPTY_DEPARTMENT_NAME("empty.department.name"),
    EMPTY_FIRSTNAME("empty.first.name"),
    EMPTY_LASTNAME("empty.lastname"),
    EMPTY_UUID("empty.uuid"),
    FIRSTNAME_PATTERN_ERROR("firstname.pattern.error"),
    INVALID_REQUEST_BODY_ERROR("invalid.request.body.error"),
    INVALID_UUID_ERROR("invalid.uuid.error"),
    INVALID_UUID_FORMAT("invalid.uuid.format"),
    LASTNAME_PATTERN_ERROR("last.name.pattern.error"),
    MISSING_ID("missing.id"),
    NO_PATIENT_FOUND("no.patient.found"),
    NO_DOCTOR_FOUND("no.doctor.found"),
    PATIENT_ASSIGNED_TO_DOCTOR("patient.assigned.to.doctor"),
    PATIENT_DELETED_SUCCESSFULLY("patient.deleted.successfully"),
    PATIENT_NOT_ASSIGNED("patient.not.assigned"),
    PATIENT_NOT_FOUND_ERROR("patient.not.found.error"),
    REPLACE_NON_EXISTENT_FIELD_NOT_ALLOWED("replace.non.existent.field.not.allowed"),
    REMOVE_OPERATION_NOT_ALLOWED("remove.operation.not.allowed"),
    SERVER_ERROR("server.error"),
    USER_NOT_FOUND_ERROR("user.not.found.error"),
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
