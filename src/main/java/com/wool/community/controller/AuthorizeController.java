package com.wool.community.controller;

import com.wool.community.dto.AccessTokenDto;
import com.wool.community.entity.GitHubUser;
import com.wool.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author WOOL
 * GitHub登录回调
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect.uri}")
    private String redirect_uri;
    @Value("${github.client.id}")
    private String client_id;

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code, @RequestParam("state") String state){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setState(state);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirect_uri);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDto);
        System.out.println(accessToken);
        GitHubUser user = gitHubProvider.getUser(accessToken);
        System.out.println(user);
        return "index";
    }



}
