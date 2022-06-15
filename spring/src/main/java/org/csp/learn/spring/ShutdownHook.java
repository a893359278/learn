package org.csp.learn.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ShutdownHook {
    public static void main(String[] args) throws IOException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ShutdownHook.class);
        context.registerShutdownHook();

        System.in.read();

    }

    @Bean
    public User user() {
        return new User();
    }

    public static class User implements DisposableBean {

        @Override
        public void destroy() throws Exception {
            System.out.println("destroy..");
        }
    }
}
