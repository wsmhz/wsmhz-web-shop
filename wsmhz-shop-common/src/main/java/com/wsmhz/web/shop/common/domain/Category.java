package com.wsmhz.web.shop.common.domain;

import com.wsmhz.security.core.domain.common.Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * create by tangbj on 2018/5/18
 */
@Table(name = "category")
public class Category extends Domain {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 父Id
     */
    private Long parentId;
    /**
     * 名称
     */
    private String name;
    /**
     * 状态
     */
    private Boolean status;
    /**
     * 排序
     */
    private Integer sortOrder;

    @Transient
    private List<Category> children = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }
}
