package com.sipuang.entwebsit.entity;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lj on 2017/5/22.
 */
@Entity
public class WsProductType {


    private Integer id;
    private String name;
    private String url;
    private boolean featured;
    private List<WsProduct> products = new ArrayList<>();

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "is_featured")
    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    @OneToMany(mappedBy = "type")
    @Cascade(value = {CascadeType.DELETE})
    @OrderBy("createDate")
    public List<WsProduct> getProducts() {
        return products;
    }

    public void setProducts(List<WsProduct> products) {
        this.products = products;
    }
}
