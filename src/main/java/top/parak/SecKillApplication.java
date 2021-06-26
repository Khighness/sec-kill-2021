package top.parak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author KHighness
 * @since 2021-06-20
 */
@SpringBootApplication
@EnableTransactionManagement
public class SecKillApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecKillApplication.class, args);
    }
}
