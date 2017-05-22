package com.sipuang.entwebsit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lj on 2017/5/19.
 */
@Entity
public class SysUser {
    /**
     * 管理员用户
     */
    public static final Integer TYPE_ADMIN = Integer.valueOf(1);

    private Integer id;
    private Integer type;
    private String nickName;
    private String userName;
    private String password;
    /**********************/
    private List<WsProduct> products = new ArrayList<>();
    private List<WsNews> newsList = new ArrayList<>();
    /**
     * 用户类型
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(length = 50)
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Column(length = 50)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @OneToMany(mappedBy = "createUser")
    public List<WsProduct> getProducts() {
        return products;
    }

    public void setProducts(List<WsProduct> products) {
        this.products = products;
    }

    @OneToMany(mappedBy = "author")
    public List<WsNews> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<WsNews> newsList) {
        this.newsList = newsList;
    }
}
