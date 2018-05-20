package mapper;

import com.github.pagehelper.PageInfo;
import com.wsmhz.web.shop.back.Main;
import com.wsmhz.web.shop.back.dao.ProductMapper;
import com.wsmhz.web.shop.back.domain.Product;
import com.wsmhz.web.shop.back.enums.ProductConst;
import com.wsmhz.web.shop.back.properties.BusinessProperties;
import com.wsmhz.web.shop.back.service.ProductService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * create by tangbj on 2018/5/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Main.class)
public class ProductTest {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private BusinessProperties businessProperties;

    @Test
    public void select(){
        List<Product> list = this.productMapper.selectAll();
        for (Product product : list) {
            System.out.println(product);
        }
    }

    @Test
    public void selectPage(){
        PageInfo<Product> pageInfo = productService.selectPageListByNameAndcategoryId(1,10,"i",1001, ProductConst.StatusEnum.ON_SALE,null);
        List<Product> list = pageInfo.getList();
        for (Product product : list) {
            System.out.println(product);
        }
    }

    @Test
    public void other(){
        String ip  = businessProperties.getFtp().getHttpPrefix();
        System.out.println(ip);
    }

}
