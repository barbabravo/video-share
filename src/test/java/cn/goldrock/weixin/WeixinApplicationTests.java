package cn.goldrock.weixin;

import cn.goldrock.entity.AccessToken;
import cn.goldrock.service.AccessService;
import cn.goldrock.utils.HttpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeixinApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void callOnHttp() {
        String result = HttpUtils.getUrl("https://www.baidu.com");
        System.out.println(result);
    }
    @Test
    public void getTime() {
        long time = new Date().getTime();
        System.out.println(time);
    }
    @Test
    public void getAccessService() {
        AccessToken accessToken = AccessService.getInstance().getAccessToken("", "", "");
        System.out.println(accessToken);
    }
}
