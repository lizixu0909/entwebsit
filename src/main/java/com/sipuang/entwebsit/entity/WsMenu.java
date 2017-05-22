package com.sipuang.entwebsit.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lj on 2017/5/22.
 */
@Entity
public class WsMenu {

    private Integer id;
    private String name;
    private Integer sequence;
    private String url;

    private WsMenu parent;

    private List<WsMenu> children = new ArrayList<>();

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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    public WsMenu getParent() {
        return parent;
    }

    public void setParent(WsMenu parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent")
    public List<WsMenu> getChildren() {
        return children;
    }

    public void setChildren(List<WsMenu> children) {
        this.children = children;
    }
}
