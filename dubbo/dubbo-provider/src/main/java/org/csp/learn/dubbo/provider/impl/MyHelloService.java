package org.csp.learn.dubbo.provider.impl;

import com.csp.learn.dubbo.api.HelloService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService(delay = 60000)
public class MyHelloService implements HelloService {
    @Override
    public void sayHello(String content) {
        System.out.println("provider say hello " + content);
    }
}
