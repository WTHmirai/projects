package com.example.usercenter.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenter.common.ErrorCode;
import com.example.usercenter.constant.UserConstant;
import com.example.usercenter.exception.BusinessException;
import com.example.usercenter.mapper.UserMapper;
import com.example.usercenter.model.User;
import com.example.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author WTH
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2024-10-28 21:06:42
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 盐值
     */
    final static String SALT = "wth";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword,String planetCode) {
        //校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword,planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数有空值");
        }
        //用户名长度校验
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名长度过短");
        }
        //密码长度校验
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度或校验码长度过短");
        }
        if (planetCode.length() > 5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号过长");
        }
        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号包含特殊字符");
        }
        //密码和校验码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码和校验码不一致");
        }

        //账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper); //调用自己服务层的count方法来执行查询
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名已存在");
        }

        //星球账号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode",planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球账号已存在");
        }
        //加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        int save = userMapper.insert(user);
        if (save != 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"向数据库保存账号失败");
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求参数有空值");
        }
        //用户名长度校验
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户名长度过短");
        }
        //密码长度校验
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码长度过短");
        }
        //账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        //加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("user login failed, userAccount or userPassword is not corrected");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在");
        }

        //用户脱敏
        User safeUser = getSaftyUser(user);

        //记录用户的登陆状态
        request.getSession().setAttribute(USER_LOGIN_STATE,safeUser);

        return safeUser;
    }

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    @Override
    public User getSaftyUser(User user){
        if(user == null) {
            return null;
        }
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setGender(user.getGender());
        safeUser.setEmail(user.getEmail());
        safeUser.setPhone(user.getPhone());
        safeUser.setCreateTime(user.getCreateTime());
        safeUser.setUpdateTime(user.getUpdateTime());
        safeUser.setUserRole(user.getUserRole());
        safeUser.setplanetCode(user.getplanetCode());
        return safeUser;
    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}
