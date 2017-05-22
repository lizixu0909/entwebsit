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
import java.util.List;

/**
 * Created by lj on 2017/5/22.
 */
@Entity
public class WsPage {

    private Integer id;
    private String title;
    private String meta;
    private String description;
    /**
     * 在菜单中的顺序
     */
    private Integer sequence;
    /**
     * 网页内容
     */
    private String content;
    /**
     * 如果是嵌入，则关联嵌入的页面，那么上面的关键字将不用再填写
     */
    private WsPage page;
    /**
     * 当前页面关联的div片段
     */
    private List<WsPage> divs = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Column(length = 20000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id")
    public WsPage getPage() {
        return page;
    }

    public void setPage(WsPage page) {
        this.page = page;
    }

    @OneToMany(mappedBy = "page")
    public List<WsPage> getDivs() {
        return divs;
    }

    public void setDivs(List<WsPage> divs) {
        this.divs = divs;
    }
}
