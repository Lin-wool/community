package com.wool.community.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 最新回复通知传输模型
 * @author WOOL
 */
@Data
@ToString
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Integer type;
    private String typeName;
    private Long outerid;
}
