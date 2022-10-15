package org.csp.learn.java.spi.impl;

import org.csp.learn.java.spi.Hello;

/**
 * @author 陈少平
 * @date 2022-10-15 15:47
 */
public class CatHelloImpl implements Hello {

    @Override
    public void hello() {
        System.out.println("cat hello!");
    }
}
