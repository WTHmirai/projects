package com.example.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.usercenter.common.BaseResponse;
import com.example.usercenter.common.ErrorCode;
import com.example.usercenter.exception.BusinessException;
import com.example.usercenter.model.User;
import com.example.usercenter.model.request.UserDeleteRequest;
import com.example.usercenter.model.request.UserLoginRequest;
import com.example.usercenter.model.request.UserRegisterRequest;
import com.example.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.usercenter.common.ResultUtils.error;
import static com.example.usercenter.common.ResultUtils.success;
import static com.example.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.example.usercenter.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数有空值");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String userPlanetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword,userPlanetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数有空值");
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword,userPlanetCode);
        return success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数有空值");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数有空值");
        }
        return success(userService.userLogin(userAccount, userPassword, request));
    }

    /**
     * 是否是管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

    @GetMapping("/current")
    public BaseResponse<User> GetCurrentUser(HttpServletRequest request) {
        Object objUser = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) objUser;
        if(user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN,"用户未登录");
        }
        long userId = user.getId();
        User endUser = userService.getById(userId);
        return success(userService.getSaftyUser(endUser));
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        //仅管理员可查询
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH,"用户权限不足");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);

        return success(userList.stream().map(user -> {
            return userService.getSaftyUser(user);
        }).collect(Collectors.toList()));
    }

    @PutMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody UserDeleteRequest userDeleteRequest, HttpServletRequest request) {
        if (userDeleteRequest.getId() <= 0 || !isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH,"用户权限不足");
        }
        return success(userService.removeById(userDeleteRequest.getId()));
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if(request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"request参数为空");
        }
        return success(userService.userLogout(request));
    }
}
