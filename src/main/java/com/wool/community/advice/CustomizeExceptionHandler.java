package com.wool.community.advice;

import com.alibaba.fastjson.JSON;
import com.wool.community.dto.ResultDTO;
import com.wool.community.exception.CustomizeErrorCode;
import com.wool.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author WOOL
 * 自定义异常处理器
 */
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handler(Throwable e, Model model,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        /** 判读是调用得接口出异常还是访问页面出异常，若是接口则返回json字符串，若为页面则跳转错误页面 */
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            if (e instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                writer.println(JSON.toJSON(resultDTO));
                writer.close();
            }catch (IOException ioe){

            }
            return null;
        }else {
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", "服务器冒烟了喂~~~");
            }
            return new ModelAndView("error");
        }
    }
}
