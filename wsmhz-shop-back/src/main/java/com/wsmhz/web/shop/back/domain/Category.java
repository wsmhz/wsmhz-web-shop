package com.wsmhz.web.shop.back.domain;

import com.wsmhz.security.core.domain.common.Domain;

/**
 * create by tangbj on 2018/5/18
 */
public class Category extends Domain {
    /**
     * Id
     */
    private Integer id;
    /**
     * 父Id
     */
    private Integer parentId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
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
}
