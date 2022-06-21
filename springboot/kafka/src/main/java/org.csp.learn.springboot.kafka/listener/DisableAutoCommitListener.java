package org.csp.learn.springboot.kafka.listener;

import java.util.concurrent.TimeUnit;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author 陈少平
 * @date 2022-06-15 21:56
 */
@Component
public class DisableAutoCommitListener {

    /**
     * 取消自动提交测试.
     * spring.kafka.consumer.enable-auto-commit = false
     */
    @KafkaListener(topics = "test2")
    public void listener(String data) {
        System.err.println("消费数据 " + data);
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
