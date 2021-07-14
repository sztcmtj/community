package com.nanxiaoqiao.community.controllers;

import com.nanxiaoqiao.community.dto.AccessTokenDTO;
import com.nanxiaoqiao.community.dto.GithubUser;
import com.nanxiaoqiao.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
public class AuthorizeController {
    @Resource(name = "githubProvider")
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect.uri}")
    private String redirect_uri;
    @GetMapping("/callback")
    public String getToken(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.getName());
        return "index";
    }
}
