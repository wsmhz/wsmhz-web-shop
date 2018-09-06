package com.wsmhz.web.shop.common.domain;
import com.wsmhz.common.data.domain.Domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * create by tangbj on 2018/5/18
 */
@Setter
@Getter
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

}
