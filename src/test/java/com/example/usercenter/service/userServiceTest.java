package com.example.usercenter.service;
import java.util.Date;

import com.example.usercenter.mapper.UserMapper;
import com.example.usercenter.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class userServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("tsetWth2");
        user.setUserAccount("1232");
        user.setAvatarUrl("https://blog.csdn.net/weixin_64122713");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setEmail("123");
        user.setPhone("123");
        boolean save = userService.save(user);
        System.out.println(user.getId());
    }

//    @Test
//    void userRegister() {
//        String userAccount = "wth1";
//        String userPassword = "";
//        String checkPassword = "123456";
//        long result = userService.userRegister(userAccount,userPassword,checkPassword);
//        Assertions.assertEquals(-1,result);
//        userAccount = "wt";
//        result = userService.userRegister(userAccount,userPassword,checkPassword);
//        Assertions.assertEquals(-1,result);
//        userAccount = "wth1";
//        userPassword = "123456";
//        result = userService.userRegister(userAccount,userPassword,checkPassword);
//        Assertions.assertEquals(-1,result);
//        userAccount = "wth1";
//        userPassword = "12345678";
//        result = userService.userRegister(userAccount,userPassword,checkPassword);
//        Assertions.assertEquals(-1,result);
//        checkPassword = "123456789";
//        result = userService.userRegister(userAccount,userPassword,checkPassword);
//        Assertions.assertEquals(-1,result);
//        userAccount = "dogwth";
//        checkPassword = "12345678";
//        result = userService.userRegister(userAccount,userPassword,checkPassword);
//        Assertions.assertEquals(-1,result);
//        userAccount = "wth1";
//        result = userService.userRegister(userAccount,userPassword,checkPassword);
//        Assertions.assertTrue(result>0);
//    }
}