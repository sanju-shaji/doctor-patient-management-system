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
    FIRSTNAME_AND_LASTNAME_PATHS_ARE_ALLOWED("firstname.and.lastname.paths.are.alowed"),
    ID_CANNOT_BE_CHANGED("id.cannot.be.changed"),
    INVALID_UUID_FORMAT("invalid.uuid.format"),
    LASTNAME_PATTERN_ERROR("last.name.pattern.error"),
    MISSING_ID("missing.id"),
    MODIFICATION_OF_PATIENT_ID_IS_NOT_ALLOWED("modification.of.patientId.is.not.allowed"),
    NO_PATIENT_FOUND("no.patient.found"),
    NO_DOCTOR_FOUND("no.doctor.found"),
    NULL_OR_EMPTY_VALUES_ARE_NOT_ALLOWED("null.or.empty.values.are.not.allowed"),
    ONLY_REPLACE_OPERATION_ARE_PERMITTED("cannot.allowed.only.'replace'.operation.are.permitted"),
    PATCH_REQUEST_FAILED("patch.request.failed"),
    PATIENT_ASSIGNED_TO_DOCTOR("patient.assigned.to.doctor"),
    PATIENT_DELETED_SUCCESSFULLY("patient.deleted.successfully"),
    PATIENT_NOT_ASSIGNED("patient.not.assigned"),
    PATIENT_NOT_FOUND("patient.not.found"),
    PATIENT_NOT_FOUND_ERROR("patient.not.found.error"),
    REPLACE_NON_EXISTENT_FIELD_NOT_ALLOWED("replace.non.existent.field.not.allowed"),
    QUERY_PARAMS_CANNOT_BE_NULL("query.params.cannot.be.null"),
    REMOVE_OPERATION_NOT_ALLOWED("remove.operation.not.allowed"),
    SERVER_ERROR("server.error"),
    USER_NOT_FOUND_ERROR("user.not.found.error");
    private final String key;

    MessageKeyEnum(String Key) {
        this.key = Key;
    }
}
