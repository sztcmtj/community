package com.nanxiaoqiao.community.dto;

import lombok.Data;

@Data
public class AccessTokenDTO {
    String client_id;
    String redirect_uri;
    String client_secret;
    String login;
    String scope;
    String state;
    String allow_signup;
    String code;
}
