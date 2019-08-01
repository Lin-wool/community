package com.wool.community.controller;

import com.wool.community.mapper.QuestionMapper;
import com.wool.community.mapper.UserMapper;
import com.wool.community.model.Question;
import com.wool.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author WOOL
 * 发布controller
 */
@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 跳转到发布页面
     * @return
     */
    @GetMapping("/publish")
    public String publish(){
        return "/publish";
    }

    /**
     * 发布信息审核
     * @return
     */
    @PostMapping("/publish")
    public String doPublish(String title, String description,
                            String tag,
                            HttpServletRequest request,
                            @CookieValue(name = "token",required = false) String token,
                            Model model){

        // 判断是否携带token并且是否和数据库中的token一致
        User user = null;
        if(token!=null&&token.length()!=0){
            user = userMapper.findByToken(token);
        }
        if(user != null){
            Question question = new Question();
            question.setTag(tag);
            question.setTitle(title);
            question.setCreator(user.getId());
            question.setDescription(description);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertQuestion(question);
        }else {
            model.addAttribute("error","用户未登录！");
            return "/publish";
        }
        return "redirect:/";
    }
}
