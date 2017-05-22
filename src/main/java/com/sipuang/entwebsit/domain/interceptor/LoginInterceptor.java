package com.sipuang.entwebsit.domain.interceptor;

import com.sipuang.entwebsit.domain.AjaxResult;
import com.sipuang.entwebsit.entity.SysUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lj on 2017/3/28.
 */
@Configuration
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getContextPath());
//        判断session是否存在
        SysUser user = (SysUser) request.getSession().getAttribute("user");
//        如果为空 则跳转到登录页面
        if (user == null) {
//            如果是前后端分离 则返回Ajax
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.getWriter().print(AjaxResult.ERROR(AjaxResult.ResultStatus.NOTLOGIN_ERROR).toString());
            return false;
        }
        return true;
    }
}
