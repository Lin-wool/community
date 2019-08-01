package com.wool.community.provider;

import com.alibaba.fastjson.JSON;
import com.wool.community.dto.AccessTokenDto;
import com.wool.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author WOOL
 * GitHub工具类
 */
@Component
public class GitHubProvider {

    /**
     *
     * 传accessTokenDto获取accessTokenDto
     * 通过code获取accessTokenDto
     * @param accessTokenDto
     * @return
     */
    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 通过access token到GitHub获取用户
     * 信息，返回自定义用户对象
     * @param accessToken
     * @return
     */
    public GitHubUser getUser(String accessToken){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            return JSON.parseObject(string,GitHubUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
