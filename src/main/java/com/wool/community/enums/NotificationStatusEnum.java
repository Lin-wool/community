package com.wool.community.enums;

/**
 * 通知是否已读枚举类
 * @author WOOL
 */
public enum NotificationStatusEnum {
    /** 未读状态 */
    UNREAD(1),
    /** 已读状态 */
    READ(2)
    ;

    NotificationStatusEnum(int status) {
        this.status = status;
    }

    private int status;

    public int getStatus() {
        return status;
    }

}
