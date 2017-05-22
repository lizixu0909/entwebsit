package com.sipuang.entwebsit.domain.lisenter;


import com.sipuang.entwebsit.entity.SysUser;
import com.sipuang.entwebsit.service.UserService;
import com.sipuang.entwebsit.util.MD5Utils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class InitLisenter implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        UserService userService = ac.getBean(UserService.class);
        if (userService.getByUserName("admin") == null) {
            SysUser admin = new SysUser();
            admin.setNickName("管理员");
            admin.setUserName("admin");
            admin.setPassword(MD5Utils.encryption("admin"));
            admin.setType(SysUser.TYPE_ADMIN);
            userService.save(admin);
        }
        System.out.println("-------------->初始化完毕<--------------");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("--------->销毁<----------");
    }

}
