package com.wool.community.controller;

import com.wool.community.cache.TagCache;
import com.wool.community.mapper.QuestionMapper;
import com.wool.community.mapper.UserMapper;
import com.wool.community.model.Question;
import com.wool.community.model.User;
import com.wool.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private QuestionService questionService;

    @GetMapping(value = "/publish/{id}")
    public String editQuestion(@PathVariable("id") Long id, Model model) {
        Question question = questionMapper.selectByPrimaryKey(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", id);
        model.addAttribute("tags", TagCache.get());
        return "/publish";
    }

    /**
     * 跳转到发布页面
     *
     * @return
     */
    @GetMapping("/publish")
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());
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
                            @RequestParam(value = "id", required = false) Long id,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        User user = (User) request.getSession().getAttribute("user");
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
        question.setId(id);
        question.setTag(tag);
        question.setTitle(title);
        question.setCreator(user.getId());
        question.setDescription(description);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }


}
