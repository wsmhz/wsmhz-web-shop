package mapper;

import com.wsmhz.web.shop.back.RBACMain;
import com.wsmhz.web.shop.common.domain.Category;
import com.wsmhz.web.shop.common.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * create by tangbj on 2018/5/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RBACMain.class)
public class CategoryTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    public void deep(){
        List<Category> list = categoryService.selectAllWithChildren(0);
        for (Category category : list) {
            System.out.println(category);
        }
    }
}
