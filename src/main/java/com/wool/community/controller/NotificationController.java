package com.wool.community.controller;

import com.wool.community.dto.NotificationDTO;
import com.wool.community.dto.ResultDTO;
import com.wool.community.enums.NotificationTypeEnum;
import com.wool.community.exception.CustomizeErrorCode;
import com.wool.community.model.User;
import com.wool.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WOOL
 * 最新回复跳转
 */
@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = "/notification/{id}")
    public String notification(@PathVariable("id") Long id, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id,user.getId());
        if(notificationDTO.getType() == NotificationTypeEnum.REPLY_QUETION.getType() || notificationDTO.getType() == NotificationTypeEnum.REPLY_COMMENT.getType()){
            return "redirect:/question/"+notificationDTO.getOuterid();
        }
        return "redirect:/";
    }
}
