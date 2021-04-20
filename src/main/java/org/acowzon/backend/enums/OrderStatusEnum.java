package org.acowzon.backend.enums;

public enum OrderStatusEnum {
    NEW("已下单",0),FINISHED("已完成",1),CANCELED("已取消",2);

    private String message;

    private int code;

    OrderStatusEnum(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
