package com.example.usercenter.model;


import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户表
 * @TableName user
 */
@Data
public class User implements Serializable {

    /**
     *
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String username;
    /**
     * 账号
     */
    private String userAccount;
    /**
     * 用户头像
     */
    private String avatarUrl;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 用户状态
     */
    private Integer userStatus;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 用户权限
     */
    private Integer userRole;
    /**
     * 星球编号
     */
    private String planetCode;
    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    /**
     *
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * 用户昵称
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * 账号
     */
    public void setUserAccount(String userAccount){
        this.userAccount = userAccount;
    }

    /**
     * 用户头像
     */
    public void setAvatarUrl(String avatarUrl){
        this.avatarUrl = avatarUrl;
    }

    /**
     * 性别
     */
    public void setGender(Integer gender){
        this.gender = gender;
    }

    /**
     * 密码
     */
    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
    }

    /**
     * 邮箱
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * 电话
     */
    public void setPhone(String phone){
        this.phone = phone;
    }

    /**
     * 用户状态
     */
    public void setUserStatus(Integer userStatus){
        this.userStatus = userStatus;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

    /**
     * 是否删除
     */
    public void setIsDelete(Integer isDelete){
        this.isDelete = isDelete;
    }


    /**
     *
     */
    public Long getId(){
        return this.id;
    }

    /**
     * 用户昵称
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * 账号
     */
    public String getUserAccount(){
        return this.userAccount;
    }

    /**
     * 用户头像
     */
    public String getAvatarUrl(){
        return this.avatarUrl;
    }

    /**
     * 性别
     */
    public Integer getGender(){
        return this.gender;
    }

    /**
     * 密码
     */
    public String getUserPassword(){
        return this.userPassword;
    }

    /**
     * 邮箱
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * 电话
     */
    public String getPhone(){
        return this.phone;
    }

    /**
     * 用户状态
     */
    public Integer getUserStatus(){
        return this.userStatus;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime(){
        return this.createTime;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime(){
        return this.updateTime;
    }

    /**
     * 是否删除
     */
    public Integer getIsDelete(){
        return this.isDelete;
    }

    public Integer getUserRole() {return this.userRole;}

    public void setUserRole(Integer role) {this.userRole = role;}

    public String getplanetCode() {return this.planetCode;}

    public void setplanetCode(String planetCode) {this.planetCode = planetCode;}
}
