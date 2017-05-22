package com.sipuang.entwebsit.service;

import com.sipuang.entwebsit.entity.SysUser;

/**
 * Created by lj on 2017/5/21.
 */
public interface UserService {
    SysUser getByUserNameAndPassword(String userName, String password);

    void resetPassWord(Integer userId, String oldPass, String newPass);

    SysUser save(SysUser user);

    SysUser getByUserName(String userName);
}
