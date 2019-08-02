package com.wool.community.controller;

import com.wool.community.mapper.UserMapper;
import com.wool.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author WOOL
 */
@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 访问首页
     */
    @RequestMapping("/")
    public String index(HttpServletRequest request, @CookieValue(name = "token", required = false) String token) {
        // 判断是否携带token并且是否和数据库中的token一致
        if (token != null && token.length() != 0) {
            User user = userMapper.findByToken(token);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
            }
        }
        return "index";
    }


}
