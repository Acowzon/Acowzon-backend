package org.acowzon.backend.enums;

public enum PaymentStatusEnum {
    WAITING("待支付",0),SUCCESS("支付成功",1),CANCELED("支付取消",2);

    private String message;

    private int code;

    PaymentStatusEnum(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
