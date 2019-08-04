package com.wool.community.controller;

import com.wool.community.dto.PaginationDTO;
import com.wool.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author WOOL
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    /**
     * 访问首页
     */
    @RequestMapping("/")
    public String index(Model model,
                        HttpServletRequest request,
                        @RequestParam(value = "page",defaultValue = "1")Integer page,
                        @RequestParam(value = "size",defaultValue = "5")Integer size) {
        // 查出问题列表存入model返回页面
        PaginationDTO paginationDTO = questionService.list(page,size);
        model.addAttribute("paginationDTO", paginationDTO);
        return "index";
    }


}
