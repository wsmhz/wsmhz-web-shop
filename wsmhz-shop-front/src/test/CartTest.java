import com.wsmhz.web.shop.common.dao.CartMapper;
import com.wsmhz.web.shop.front.ShopMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * create by tangbj on 2018/5/27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShopMain.class)
public class CartTest {
    @Autowired
    private CartMapper cartMapper;

    @Test
    public void zfbInfo() throws Exception {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:zfbInfo.properties");
            System.out.println(file.getPath());
            System.out.println(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
