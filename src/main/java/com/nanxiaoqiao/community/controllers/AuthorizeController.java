package com.nanxiaoqiao.community.controllers;

import com.nanxiaoqiao.community.dto.AccessTokenDTO;
import com.nanxiaoqiao.community.dto.GithubUser;
import com.nanxiaoqiao.community.provider.GithubProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
public class AuthorizeController {
    @Resource(name = "githubProvider")
    private GithubProvider githubProvider;
    @GetMapping("/callback")
    public String getToken(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("4ad6d093d481928f8fda");
        accessTokenDTO.setClient_secret("d56c2df555d11ed00e91a3e1db190c7a237eaf7f");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8999/callback");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        System.out.println(githubUser.getName());
        return "index";
    }
}
