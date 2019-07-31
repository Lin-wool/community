package com.wool.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author WOOL
 */
@Controller
public class IndexController {

    /**
     * 访问首页
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }


}
