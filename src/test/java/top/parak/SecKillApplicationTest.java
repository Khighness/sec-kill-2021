package top.parak;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.parak.redis.RedisService;
import top.parak.service.GoodsService;

@SpringBootTest
class SecKillApplicationTest {
    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Test
    public void query() {
        goodsService.getGoodsVOList().forEach(System.out::println);
    }

}
