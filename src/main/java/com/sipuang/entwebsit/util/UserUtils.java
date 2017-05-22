package com.sipuang.entwebsit.util;

import com.sipuang.entwebsit.entity.SysUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lj on 2017/5/21.
 */
public class UserUtils {
    /**
     HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

     HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();

     HttpServletResponse response = ((ServletWebRequest)RequestContextHolder.getRequestAttributes()).getResponse();

     ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
     */



    public static SysUser getUser(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysUser user = (SysUser) request.getSession().getAttribute("user");
        return user;
    }

    public static Integer getUserId(){
        return getUser().getId();
    }
}
