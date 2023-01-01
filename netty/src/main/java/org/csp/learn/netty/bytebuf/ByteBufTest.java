package org.csp.learn.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.ReferenceCountUtil;

/**
 * @author 陈少平
 * @date 2022-12-12 23:19
 */
public class ByteBufTest {

    public static void main(String[] args) {
        ByteBufTest test = new ByteBufTest();
        test.backingArray();
    }

    public void backingArray() {
        ByteBuf buf = ByteBufAllocator.DEFAULT.heapBuffer();
        if (buf.hasArray()) {
        }
    }
}
