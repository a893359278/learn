package org.csp.learn.serialization;

import org.csp.learn.serialization.constant.OperatorEnum;
import org.junit.Test;

/**
 * @author 陈少平
 * @date 2023-05-18 23:16
 */
public class HessianSerialization {


    @Test
    public void test1() {
        Byte b = Byte.MAX_VALUE;
        // 序列化Byte类型
        final byte[] bytes = HessianSerializer.serializeObject(b);
        final Object newObject = HessianSerializer.deserializeObject(bytes);
        System.out.println(newObject);
        // 反序列化后类型应该还是Byte，所以理论上这里可以反序列化
        Byte newB = (Byte)newObject;
    }

    @Test
    public void test2() {
        byte[] bytes = HessianSerializer.serializeObject(OperatorEnum.ADD);
        Object object = HessianSerializer.deserializeObject(bytes);
        System.out.println(object);
    }
}
