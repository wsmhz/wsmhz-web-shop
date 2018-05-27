package com.wsmhz.web.shop.common.dto;

import com.wsmhz.web.shop.common.enums.ProductConst;

import java.math.BigDecimal;

/**
 * create by tangbj on 2018/5/26
 */
public class CartProductDto {

    private Long id;

    private Long userId;

    private Long productId;

    private Integer quantity;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private ProductConst.StatusEnum productStatus;

    private BigDecimal productTotalPrice;

    private Integer productStock;

    private Boolean productChecked;

    private Boolean stockEnough; //库存是否充足

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSubtitle() {
        return productSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        this.productSubtitle = productSubtitle;
    }

    public String getProductMainImage() {
        return productMainImage;
    }

    public void setProductMainImage(String productMainImage) {
        this.productMainImage = productMainImage;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public ProductConst.StatusEnum getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductConst.StatusEnum productStatus) {
        this.productStatus = productStatus;
    }

    public BigDecimal getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(BigDecimal productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public Boolean getProductChecked() {
        return productChecked;
    }

    public void setProductChecked(Boolean productChecked) {
        this.productChecked = productChecked;
    }

    public Boolean getStockEnough() {
        return stockEnough;
    }

    public void setStockEnough(Boolean stockEnough) {
        this.stockEnough = stockEnough;
    }
}
