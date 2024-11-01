package com.example.usercenter.model.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录请求体
 */
@Data
public class UserLoginRequest implements Serializable {


    @Serial
    private static final long serialVersionUID = 3415272482434475107L;
    /**
     * 用户名、用户密码、校验码
     */
    private String userAccount;
    private String userPassword;
}
