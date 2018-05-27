package com.wsmhz.web.shop.common.domain;

import com.wsmhz.security.core.domain.common.Domain;
import com.wsmhz.web.shop.common.enums.ProductConst;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
/**
 * create by tangbj on 2018/5/18
 */
@Table(name = "product")
public class Product extends Domain {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 分类Id
     */
    private Long categoryId;
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
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private ProductConst.StatusEnum status;
    /**
     * 标志（扩展）
     */
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private ProductConst.FlagEnum flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
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

    public ProductConst.StatusEnum getStatus() {
        return status;
    }

    public void setStatus(ProductConst.StatusEnum status) {
        this.status = status;
    }

    public ProductConst.FlagEnum getFlag() {
        return flag;
    }

    public void setFlag(ProductConst.FlagEnum flag) {
        this.flag = flag;
    }
}
