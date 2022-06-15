package org.csp.learn.springboot.http2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void customize(UndertowServletWebServerFactory factory) {
        factory.addBuilderCustomizers((UndertowBuilderCustomizer) builder -> {
            builder.addHttpListener(8080, "0.0.0.0");
        });
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
