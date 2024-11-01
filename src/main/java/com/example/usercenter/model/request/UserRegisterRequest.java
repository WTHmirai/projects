package com.example.usercenter.model.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求体
 */
@Data
public class UserRegisterRequest implements Serializable {


    @Serial
    private static final long serialVersionUID = -4395157730514752411L;
    /**
     * 用户名、用户密码、校验码
     */
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;
}
