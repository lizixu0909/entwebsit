package com.sipuang.entwebsit.repository;

import com.sipuang.entwebsit.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lj on 2017/5/19.
 */
public interface UserRepository extends JpaRepository<SysUser, Integer> {
    SysUser findByUserNameAndPassword(String userName, String password);

    SysUser findByUserName(String userName);
}
