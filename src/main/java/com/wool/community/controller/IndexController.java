package com.wool.community.controller;

import com.wool.community.dto.QuestionDTO;
import com.wool.community.mapper.QuestionMapper;
import com.wool.community.mapper.UserMapper;
import com.wool.community.model.User;
import com.wool.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author WOOL
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    /**
     * 访问首页
     */
    @RequestMapping("/")
    public String index(Model model,
                        HttpServletRequest request,
                        @RequestParam(value = "page",defaultValue = "1")Integer page,
                        @RequestParam(value = "size",defaultValue = "5")Integer size,
                        @CookieValue(name = "token", required = false) String token) {
        // 判断是否携带token并且是否和数据库中的token一致
        if (token != null && token.length() != 0) {
            User user = userMapper.findByToken(token);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
            }
        }
        // 查出问题列表存入model返回页面
        List<QuestionDTO> questionDTOList = questionService.list(page,size);
        model.addAttribute("questions", questionDTOList);
        return "index";
    }


}
