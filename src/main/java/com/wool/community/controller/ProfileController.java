package com.wool.community.controller;

import com.wool.community.dto.NotificationDTO;
import com.wool.community.dto.PaginationDTO;
import com.wool.community.model.User;
import com.wool.community.service.NotificationService;
import com.wool.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wool
 * 个人控制中心
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping(value = "/profile/{section}")
    public String profile(Model model,
                          HttpServletRequest request,
                          @PathVariable("section") String section,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "5") Integer size) {

        // 判断是否携带token并且是否和数据库中的token一致
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "/";
        }


        if ("questions".equals(section)) {
            PaginationDTO paginationDTO = questionService.listByCreator(user.getId(), page, size);
            model.addAttribute("paginationDTO", paginationDTO);
            model.addAttribute("sectionName", "我的问题");
            model.addAttribute("section", "questions");
        } else if ("replies".equals(section)) {
            PaginationDTO paginationDTO = notificationService.list(user.getId(),page,size);
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("paginationDTO", paginationDTO);
            model.addAttribute("section", "replies");
        }
        return "/profile";
    }
}
