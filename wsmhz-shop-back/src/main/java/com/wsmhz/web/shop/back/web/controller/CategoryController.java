package com.wsmhz.web.shop.back.web.controller;

import com.github.pagehelper.PageInfo;
import com.wsmhz.common.business.dto.MybatisPage;
import com.wsmhz.common.data.response.ServerResponse;
import com.wsmhz.web.shop.common.domain.Category;
import com.wsmhz.web.shop.common.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * create by tangbj on 2018/5/19
 */
@RestController
@RequestMapping("/manage/category")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public ServerResponse<MybatisPage> selectAllOfPage(@RequestParam(value = "pageNum")Integer pageNum,
                                                       @RequestParam(value = "pageSize")Integer pageSize,
                                                       @RequestParam(value = "name",required = false)String name,
                                                       @RequestParam(value = "status",required = false)Boolean status){
        PageInfo<Category> pageInfo = categoryService.selectPageListByNameAndStatus(pageNum,pageSize,name,status);
        return  ServerResponse.createBySuccess(new MybatisPage<>(pageInfo.getTotal(),pageInfo.getList()));
    }

    @GetMapping("/{id}")
    public ServerResponse<Category> select(@PathVariable("id")Long id){
        return  ServerResponse.createBySuccess(categoryService.selectByPrimaryKey(id));
    }

    @GetMapping()
    public ServerResponse<List<Category>> selectAll(){
        return  ServerResponse.createBySuccess(categoryService.selectAll());
    }

    @PostMapping
    public ServerResponse<String> insert(@RequestBody Category category){
        Integer result = categoryService.insertSelective(category);
        if(result > 0){
            return  ServerResponse.createBySuccessMessage("新增成功");
        }else{
            return  ServerResponse.createByErrorMessage("新增失败");
        }
    }

    @PutMapping
    public ServerResponse<String> update(@RequestBody Category category){
        Integer result = categoryService.updateByPrimaryKeySelective(category);
        if(result > 0){
            return  ServerResponse.createBySuccessMessage("修改成功");
        }else{
            return  ServerResponse.createByErrorMessage("修改失败");
        }
    }

    @DeleteMapping("/{id}")
    public ServerResponse<String> delete(@PathVariable("id") Long id){
        Integer result = categoryService.deleteByPrimaryKey(id);
        if(result > 0){
            return  ServerResponse.createBySuccessMessage("删除成功");
        }else{
            return  ServerResponse.createByErrorMessage("删除失败");
        }
    }
}
