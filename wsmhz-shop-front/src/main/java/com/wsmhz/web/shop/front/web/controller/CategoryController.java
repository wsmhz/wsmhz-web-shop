package com.wsmhz.web.shop.front.web.controller;

import com.wsmhz.security.core.common.ServerResponse;
import com.wsmhz.web.shop.common.domain.Category;
import com.wsmhz.web.shop.common.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * create by tangbj on 2018/5/19
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ServerResponse<List<Category>> selectAll(){
        return  ServerResponse.createBySuccess(categoryService.selectAllWithChildren(new Integer(0).longValue()));
    }

    @GetMapping("/{id}")
    public ServerResponse<List<Category>> selectByParentId(@PathVariable("id")Long id){
        return  ServerResponse.createBySuccess(categoryService.selectByParentId(id));
    }


}
