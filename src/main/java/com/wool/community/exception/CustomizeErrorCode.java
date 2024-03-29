package com.wool.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"问题不存在，换一个试试呗~~~"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复~~~"),
    NO_LOGIN(2003,"用户未登录，请登录后操作~~~"),
    SYS_ERROR(2004,"服务器冒烟啦~~~"),
    TYPE_PARAM_WRONG (2005,"评论类型错误或不存在~"),
    COMMENT_NOT_FOUND(2006,"您回复得评论不存在或已删除~~~"),
    COMMENT_IS_EMPTY(2007,"评论内容为空~~~"),
    READ_NOTIFICATION_FAIL(2008,"兄弟，你这是读别人得信息呢？"),
    NOTIFICATION_NOT_FOUND(2009,"通知信息不翼而飞了~"),
    FAIL_UPLOAD_ERROR(2010,"文件上传失败！");

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code,String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
