package com.wool.community.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author WOOL
 * 文件上传模型
 */
@Data
@ToString
public class FileUploadDTO {
    /** 上传回传的信息 */
    private String msg;
    /** 上传之后返回的回显地址 */
    private String url;
    /** 0表示失败 1表示成功*/
    private Integer success;
}
