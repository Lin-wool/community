package com.wool.community.controller;

import com.wool.community.mapper.UserMapper;
import com.wool.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String index(HttpServletRequest request){
        // 判断是否携带token并且是否和数据库中的token一致
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length!=0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    System.out.println(user);
                    if(user != null){
                        HttpSession session = request.getSession();
                        session.setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        return "index";
    }


}
