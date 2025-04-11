package com.elixrlabs.doctorpatientmanagementsystem.enums;

import lombok.Getter;

/**
 * enum representing  keys to retrieve messages from the messages.properties file.
 */
@Getter
public enum MessageKeyEnum {
    BLANK_UUID("blank.uuid"),
    FAIL_TO_APPLY_PATCH("failed to apply patch"),
    FIRSTNAME_AND_LASTNAME_PATHS_ARE_ALLOWED("firstname.and.lastname.paths.are.alowed"),
    ID_CANNOT_BE_CHANGED("id.cannot.be.changed"),
    INVALID_UUID_FORMAT("invalid.uuid.format"),
    MODIFICATION_OF_PATIENT_ID_IS_NOT_ALLOWED("modification.of.patientId.is.not.allowed"),
    NO_PATIENT_FOUND("no.patient.found"),
    NULL_OR_EMPTY_VALUES_ARE_NOT_ALLOWED("null.or.empty.values.are.not.allowed"),
    ONLY_REPLACE_OPERATION_ARE_PERMITTED("cannot.allowed.only.'replace'.operation.are.permitted"),
    PATCH_REQUEST_FAILED("patch.request.failed"),
    PATIENT_ASSIGNED_TO_DOCTOR("patient.assigned.to.doctor"),
    PATIENT_DELETED_SUCCESSFULLY("patient.deleted.successfully");
    private final String key;

    MessageKeyEnum(String Key) {
        this.key = Key;
    }

}
