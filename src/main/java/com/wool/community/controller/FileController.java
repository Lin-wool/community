package com.wool.community.controller;

import com.wool.community.dto.FileUploadDTO;
import com.wool.community.exception.CustomizeErrorCode;
import com.wool.community.exception.CustomizeException;
import com.wool.community.provider.UCloudProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author WOOL
 * 文件上传
 */
@Controller
public class FileController {
    @Autowired
    private UCloudProvider uCloudProvider;

    @ResponseBody
    @RequestMapping(value = "/file/upload")
    public FileUploadDTO fileUpload(HttpServletRequest request){

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("editormd-image-file");
        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        try {
            String url = uCloudProvider.upload(file.getInputStream(), file.getContentType(), file.getOriginalFilename());
            fileUploadDTO.setMsg("上传成功~！");
            fileUploadDTO.setUrl(url);
            fileUploadDTO.setSuccess(1);
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomizeException(CustomizeErrorCode.FAIL_UPLOAD_ERROR);
        }
        return fileUploadDTO;
    }

}
