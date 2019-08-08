package com.wool.community.enums;

/**
 * 通知类型枚举类
 * @author WOOL
 */
public enum NotificationTypeEnum {
    /**
     * 回复了问题
     */
    REPLY_QUETION(1,"回复了问题"),
    /**
     * 回复了评论
     */
    REPLY_COMMENT(2,"回复了评论")
    ;

    NotificationTypeEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static String nameOfType(Integer type){
        for (NotificationTypeEnum notificationTypeEnum : NotificationTypeEnum.values()) {
            if(notificationTypeEnum.getType() == type){
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
