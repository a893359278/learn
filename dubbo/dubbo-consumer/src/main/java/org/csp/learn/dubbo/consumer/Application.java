package org.csp.learn.dubbo.consumer;

import java.io.IOException;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.csp.learn.dubbo.consumer.comp.MyHelloServiceConsumer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

public class Application {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Bootstrap.class);
        context.start();
        MyHelloServiceConsumer bean = context.getBean(MyHelloServiceConsumer.class);
        bean.hello();
        context.close();
        System.in.read();
    }

    @EnableDubbo(scanBasePackages = "org.csp.learn.dubbo.consumer.comp")
    @ComponentScan(value = {"org.csp.learn.dubbo.consumer.comp"})
    @PropertySource("classpath:/spring/dubbo-consumer.properties")
    static class Bootstrap {
    }
}
