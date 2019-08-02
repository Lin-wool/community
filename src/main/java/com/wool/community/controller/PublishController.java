package com.wool.community.controller;

import com.wool.community.mapper.QuestionMapper;
import com.wool.community.mapper.UserMapper;
import com.wool.community.model.Question;
import com.wool.community.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
     *
     * @return
     */
    @GetMapping("/publish")
    public String publish() {
        return "/publish";
    }

    /**
     * 发布信息审核
     *
     * @return
     */
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "tag", required = false) String tag,
                            HttpServletRequest request,
                            @CookieValue(name = "token", required = false) String token,
                            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        // 判断是否携带token并且是否和数据库中的token一致
        User user = null;
        if (token != null && token.length() != 0) {
            user = userMapper.findByToken(token);
        }
        // 验证用户是否登录
        if (user == null) {
            model.addAttribute("error", "用户未登录！");
            return "/publish";
        }
        // 表单验证
        if (title == null || title.equals("")) {
            model.addAttribute("error", "标题不能为空！");
            return "/publish";
        }
        if (description == null || description.equals("")) {
            model.addAttribute("error", "问题补充不能为空！");
            return "/publish";
        }
        if (tag == null || tag.equals("")) {
            model.addAttribute("error", "标签不能为空！");
            return "/publish";
        }
        Question question = new Question();
        question.setTag(tag);
        question.setTitle(title);
        question.setCreator(user.getId());
        question.setDescription(description);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.insertQuestion(question);
        return "redirect:/";
    }
}
