package com.wsmhz.web.shop.back.domain;

import com.wsmhz.security.core.domain.common.Domain;
import java.math.BigDecimal;
/**
 * create by tangbj on 2018/5/18
 */
public class Product extends Domain {
    /**
     * Id
     */
    private Integer id;
    /**
     * 分类Id
     */
    private Integer categoryId;
    /**
     * 名称
     */
    private String name;
    /**
     * 子标题
     */
    private String subtitle;
    /**
     * 主图
     */
    private String mainImage;
    /**
     * 子图
     */
    private String subImages;
    /**
     * 详情
     */
    private String detail;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 库存
     */
    private Integer stock;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 标志（扩展）
     */
    private Integer flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
