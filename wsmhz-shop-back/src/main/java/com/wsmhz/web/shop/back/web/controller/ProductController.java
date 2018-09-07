package com.wsmhz.web.shop.back.web.controller;

import com.github.pagehelper.PageInfo;
import com.wsmhz.common.business.dto.MybatisPage;
import com.wsmhz.common.data.response.ServerResponse;
import com.wsmhz.web.shop.common.domain.Product;
import com.wsmhz.web.shop.common.enums.ProductConst;
import com.wsmhz.web.shop.common.service.FileService;
import com.wsmhz.web.shop.common.service.ProductService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * create by tangbj on 2018/5/19
 */
@RestController
@RequestMapping("/manage/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;

    @GetMapping("/page")
    public ServerResponse<MybatisPage> selectAllOfPage(@RequestParam(value = "pageNum")Integer pageNum,
                                                       @RequestParam(value = "pageSize")Integer pageSize,
                                                       @RequestParam(value = "name",required = false)String name,
                                                       @RequestParam(value = "categoryId",required = false)Long categoryId,
                                                       @RequestParam(value = "status",required = false)ProductConst.StatusEnum status,
                                                       @RequestParam(value = "flag",required = false)ProductConst.FlagEnum flag){
        PageInfo<Product> pageInfo = productService.selectPageListByNameAndCategoryId(pageNum,pageSize,name,categoryId,status,flag);
        return  ServerResponse.createBySuccess(new MybatisPage<>(pageInfo.getTotal(),pageInfo.getList()));
    }

    @GetMapping("/{id}")
    public ServerResponse<Product> select(@PathVariable("id")Long id){
        return  ServerResponse.createBySuccess(productService.selectByPrimaryKey(id));
    }

    @PostMapping
    public ServerResponse<String> insert(@RequestBody Product product){
        if(StringUtils.isNotBlank(product.getSubImages())){
            String[] subImages = product.getSubImages().split(",");
            if(subImages.length > 0){
                product.setMainImage(subImages[0]);
            }
        }
        Integer result = productService.insertSelective(product);
        if(result > 0){
            return  ServerResponse.createBySuccessMessage("新增成功");
        }else{
            return  ServerResponse.createByErrorMessage("新增失败");
        }
    }

    @PutMapping
    public ServerResponse<String> update(@RequestBody Product product){
        if(StringUtils.isNotBlank(product.getSubImages())){
            String[] subImages = product.getSubImages().split(",");
            if(subImages.length > 0){
                product.setMainImage(subImages[0]);
            }
        }
        Integer result = productService.updateByPrimaryKeySelective(product);
        if(result > 0){
            return  ServerResponse.createBySuccessMessage("修改成功");
        }else{
            return  ServerResponse.createByErrorMessage("修改失败");
        }
    }

    @DeleteMapping("/{id}")
    public ServerResponse<String> delete(@PathVariable("id") Long id){
        Integer result = productService.deleteByPrimaryKey(id);
        if(result > 0){
            return  ServerResponse.createBySuccessMessage("删除成功");
        }else{
            return  ServerResponse.createByErrorMessage("删除失败");
        }
    }

    @PostMapping("/file")
    public ServerResponse<String> upload(@RequestPart(value = "file") MultipartFile file, HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("upload");
        String url = fileService.upload(file, path);
        if(StringUtils.isNotBlank(url)){
            return ServerResponse.createBySuccess("上传成功",url);
        }
        return ServerResponse.createByErrorMessage("上传失败");
    }

    @PutMapping("/file")
    public ServerResponse<String> deleteFile(@RequestParam("file") String file) {
        boolean result = fileService.delete(file);
        if(result){
            return ServerResponse.createBySuccess("删除成功");
        }
        return ServerResponse.createByErrorMessage("删除失败");
    }

}
