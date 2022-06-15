package org.csp.learn.dubbo.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Bootstrap.class);
        context.start();
        System.in.read();
    }

    @EnableDubbo(scanBasePackages = "org.csp.learn.dubbo.provider.impl")
    @PropertySource("classpath:/spring/dubbo-provider.properties")
    @ComponentScan(value = {"org.csp.learn.dubbo.provider.impl"})
    static class Bootstrap {
    }
}
