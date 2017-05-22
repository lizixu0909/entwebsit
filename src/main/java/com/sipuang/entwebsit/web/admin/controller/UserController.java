package com.sipuang.entwebsit.web.admin.controller;

import com.sipuang.entwebsit.domain.AjaxResult;
import com.sipuang.entwebsit.entity.SysUser;
import com.sipuang.entwebsit.service.UserService;
import com.sipuang.entwebsit.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lj on 2017/5/19.
 */
@RestController
@RequestMapping("admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public String login(String userName, String password) {
        SysUser user = userService.getByUserNameAndPassword(userName, password);
        return "";
    }

    @PostMapping("resetPass")
    public AjaxResult resetPass(String oldPass, String newPass){
        if (oldPass.equals(newPass)){
            userService.resetPassWord(UserUtils.getUserId(), oldPass, newPass);
        }
        return new AjaxResult();
    }

}
