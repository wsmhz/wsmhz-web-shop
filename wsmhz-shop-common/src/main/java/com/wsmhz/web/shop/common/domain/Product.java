package com.wsmhz.web.shop.common.domain;
import com.wsmhz.common.business.domain.Domain;
import com.wsmhz.web.shop.common.enums.ProductConst;
import lombok.Getter;
import lombok.Setter;
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
@Setter
@Getter
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

}
