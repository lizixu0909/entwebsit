package com.sipuang.entwebsit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 产品信息
 */
@Entity
public class WsProduct {

    private Integer id;
    private String name;
    private Double price;
    private List<WsProductImage> images = new ArrayList<>();
    /**
     * 描述, 支持html
     */
    private String introduction;
    private Date createDate;
    private Date updateDate;
    /**
     * 产品类型
     */
    private WsProductType type;
    /**
     * 创建用户
     */
    private SysUser createUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 4000)
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @OneToMany(mappedBy = "product")
    public List<WsProductImage> getImages() {
        return images;
    }

    public void setImages(List<WsProductImage> images) {
        this.images = images;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id")
    public WsProductType getType() {
        return type;
    }

    public void setType(WsProductType type) {
        this.type = type;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id")
    public SysUser getCreateUser() {
        return createUser;
    }

    public void setCreateUser(SysUser createUser) {
        this.createUser = createUser;
    }

    @Column(columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Column(columnDefinition = "TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP default CURRENT_TIMESTAMP")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
