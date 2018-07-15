import com.wsmhz.web.shop.common.dao.OrderMapper;
import com.wsmhz.web.shop.common.dao.ProductMapper;
import com.wsmhz.web.shop.common.domain.Order;
import com.wsmhz.web.shop.front.ShopMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * create by tangbj on 2018/5/27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShopMain.class)
public class OrderTest {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;

    @Test
    public void selectAll(){
        List<Order> list = orderMapper.selectAll();
        System.out.println(list);
    }

    @Test
    public void selectStockByProductIdTest(){
        int stock  = productMapper.selectStockByProductId(Long.valueOf(1003));
        System.out.println(stock);
    }
}
