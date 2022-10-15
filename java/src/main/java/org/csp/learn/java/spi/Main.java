package org.csp.learn.java.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author 陈少平
 * @date 2022-10-15 15:48
 */
public class Main {

    public static void main(String[] args) {
        ServiceLoader<Hello> load = ServiceLoader.load(Hello.class);
        Iterator<Hello> iterator = load.iterator();
        while (iterator.hasNext()) {
            Hello item = iterator.next();
            // 分别打印
            // cat hello!
            // dog hello!
            item.hello();
        }
    }
}
