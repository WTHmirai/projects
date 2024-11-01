package com.example.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usercenter.model.User;
import jakarta.servlet.http.HttpServletRequest;


//用户服务
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @param checkPassword 校验码
     * @return 用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword,String userPlanetCode);

    /**
     * 用户登录
     * @param userAccount 用户名
     * @param userPassword 用户密码
     * @param request 请求
     * @return 用户类
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    User getSaftyUser(User user);

    /**
     * 用户注销
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
}
