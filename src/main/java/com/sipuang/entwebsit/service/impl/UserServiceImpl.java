package com.sipuang.entwebsit.service.impl;

import com.sipuang.entwebsit.domain.exception.CustomServiceException;
import com.sipuang.entwebsit.entity.SysUser;
import com.sipuang.entwebsit.repository.UserRepository;
import com.sipuang.entwebsit.service.UserService;
import com.sipuang.entwebsit.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lj on 2017/5/21.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public SysUser getByUserNameAndPassword(String userName, String password) {
        SysUser user = userRepository.findByUserNameAndPassword(userName, MD5Utils.encryption(password));
        return user;
    }

    @Override
    public void resetPassWord(Integer userId, String oldPass, String newPass) {
        if (oldPass.equals(newPass)) {
            throw new CustomServiceException("原密码和新密码不能相同");
        }
        SysUser user = userRepository.findOne(userId);
        if (user == null) {
            throw new CustomServiceException("用户不存在");
        }
        if (!user.getPassword().equals(MD5Utils.encryption(oldPass))) {
            throw new CustomServiceException("原密码错误");
        }
        user.setPassword(MD5Utils.encryption(newPass));
        userRepository.save(user);
    }

    @Override
    public SysUser save(SysUser user) {
        user.setId(null);
        return userRepository.save(user);
    }

    @Override
    public SysUser getByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
