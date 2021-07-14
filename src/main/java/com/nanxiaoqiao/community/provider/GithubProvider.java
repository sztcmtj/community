package com.nanxiaoqiao.community.provider;

import com.alibaba.fastjson.JSON;
import com.nanxiaoqiao.community.dto.AccessTokenDTO;
import com.nanxiaoqiao.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component("githubProvider")
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        final MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            if (!StringUtils.isEmpty(str)) {
                String token = str.split("&")[0].split("=")[1];
                return token;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "token " + accessToken).build();

        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            return JSON.parseObject(str, GithubUser.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
