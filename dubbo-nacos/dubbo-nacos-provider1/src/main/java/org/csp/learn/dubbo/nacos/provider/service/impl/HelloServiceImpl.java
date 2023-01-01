package org.csp.learn.dubbo.nacos.provider.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.csp.learn.dubbo.nacos.provider.api.service.HelloService;

/**
 * @author 陈少平
 * @date 2022-11-21 21:07
 */
@DubboService(timeout = 4000, weight = 122, actives = 100, protocol = "dubbo", delay = 5000)
public class HelloServiceImpl implements HelloService {

    @Override
    public void hello(String msg) {
        System.out.println("i am provider1 " + msg);
    }
}
