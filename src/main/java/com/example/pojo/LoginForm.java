package com.example.pojo;

import lombok.Data;

/*
* 用户登录表单信息
* */
@Data
public class LoginForm {

    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;

}
