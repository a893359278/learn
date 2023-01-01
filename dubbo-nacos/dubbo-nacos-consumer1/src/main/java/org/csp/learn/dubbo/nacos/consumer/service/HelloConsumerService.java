package org.csp.learn.dubbo.nacos.consumer.service;

import org.apache.dubbo.config.annotation.DubboReference;
import org.csp.learn.dubbo.nacos.provider.api.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈少平
 * @date 2022-11-21 21:13
 */
@RestController
public class HelloConsumerService {

    @DubboReference(protocol = "dubbo")
    private HelloService helloService;

    @GetMapping("/consumeHello")
    public String consumeHello(String msg) {
        helloService.hello(msg);
        return msg;
    }
}
