package com.wool.community.enums;

/**
 * @author WOOL
 * 评论类型枚举类
 */
public enum CommentTypeEnum {
    /**
     * 问题回复
     */
    QUESTION(1),
    /**
     * 评论回复
     */
    COMMENT(2);
    private Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if (commentTypeEnum.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
