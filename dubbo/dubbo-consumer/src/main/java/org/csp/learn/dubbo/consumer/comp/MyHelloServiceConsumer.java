package org.csp.learn.dubbo.consumer.comp;

import com.csp.learn.dubbo.api.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

@Component
public class MyHelloServiceConsumer {
    @DubboReference
    HelloService helloService;

    public void hello() {
        helloService.sayHello("hello");
    }
}
