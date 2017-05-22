package com.sipuang.entwebsit.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Created by lj on 2017/5/22.
 */
@Entity
public class WsProductImage {

    private Integer id;
    private String path;
    private String name;
    /**
     * 产品
     */
    private WsProduct product;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    public WsProduct getProduct() {
        return product;
    }

    public void setProduct(WsProduct product) {
        this.product = product;
    }
}
