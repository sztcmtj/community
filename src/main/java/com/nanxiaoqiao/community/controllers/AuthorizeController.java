package com.nanxiaoqiao.community.controllers;

import com.nanxiaoqiao.community.dto.AccessTokenDTO;
import com.nanxiaoqiao.community.dto.GithubUser;
import com.nanxiaoqiao.community.mapper.UserMapper;
import com.nanxiaoqiao.community.model.User;
import com.nanxiaoqiao.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String getToken(@RequestParam(name = "code")String code ,
                           @RequestParam(name = "state")String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null && githubUser.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatar_url());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModify(System.currentTimeMillis());
            response.addCookie(new Cookie("token", token));
            userMapper.insertUser(user);
            return "redirect:/";
        } else {
            // 登陆失败，重新登陆
            return "redirect:/";
        }
    }
}
