package org.csp.learn.sharding;

import javax.annotation.Resource;
import org.csp.learn.sharding.service.MyService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author 陈少平
 * @date 1/27/24 10:24 AM
 */
@SpringBootApplication
@MapperScan(basePackages = {"org.csp.learn.sharding.mapper"})
@EnableTransactionManagement
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Resource
    MyService myService;

    @Override
    public void run(String... args) throws Exception {
        myService.save();
    }
}
