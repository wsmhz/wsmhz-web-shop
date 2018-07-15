package mapperTest;

import com.wsmhz.web.shop.back.RBACMain;
import com.wsmhz.web.shop.common.dao.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * create by tangbj on 2018/7/15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RBACMain.class)
public class OrderTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void monthOrder() {
        List<Map<String,String>> list = orderMapper.selectMonthOrders(1);
        System.out.println(list);
    }
}
