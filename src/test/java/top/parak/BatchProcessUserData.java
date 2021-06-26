package top.parak;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import top.parak.dao.OrderDao;
import top.parak.dao.UserDao;
import top.parak.domain.OrderInfo;
import top.parak.domain.User;
import top.parak.redis.AuthKey;
import top.parak.redis.RedisService;
import top.parak.util.MD5Util;
import top.parak.util.RandomInfoGenerator;

import java.io.*;
import java.util.UUID;

/**
 * @author KHighness
 * @since 2021-06-21
 */
@EnableTransactionManagement
@SpringBootTest
public class BatchProcessUserData {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderDao orderDao;

    // 批量生产用户数据，插入到数据库，并生成Token写入文件
    @Test
    public void insert() throws IOException {
        File file = new File("C:/Users/18236/Desktop/Config.txt");
        if (!file.exists()) file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.flush();
        for (int i = 11; i <= 5010; i++) {
            String salt = MD5Util.salt(8);
            StringBuilder pass = new StringBuilder();
            for (int k = 0; k < 6; k++) pass.append((i + 1));
            String mobile = RandomInfoGenerator.getTelephone();
            String password = MD5Util.inputPassToDBPass(pass.toString(), salt);
            User user = new User((long)i, mobile,
                    RandomInfoGenerator.getSexAndChineseName()[1],
                    password, salt,
                    i + ".jpg",
                    RandomInfoGenerator.getDateTime(2018, 2019),
                    RandomInfoGenerator.getDateTime(2020, 2021),
                    (long) RandomInfoGenerator.getNum(10, 99999)
            );
            String token = UUID.randomUUID().toString();
            redisService.set(AuthKey.token, token, user);
            userDao.saveUser(user);
            String line = 1 + "," + token + "\n";
            outputStream.write(line.getBytes());
        }
        outputStream.close();
    }
}
