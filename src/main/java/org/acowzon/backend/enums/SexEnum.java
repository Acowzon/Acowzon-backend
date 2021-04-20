package org.acowzon.backend.enums;

public enum SexEnum {
    MALE("男",0),FEMALE("女",1),SECRET("保密",3);

    private String message;
    private int code;

    SexEnum(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
