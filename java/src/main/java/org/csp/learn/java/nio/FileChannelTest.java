package org.csp.learn.java.nio;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author 陈少平
 * @date 2022-09-17 18:11
 */
public class FileChannelTest {


    public void transfer() throws IOException {
        FileChannel channel = FileChannel.open(Paths.get("/Users/chenshaoping/test.txt"), StandardOpenOption.READ);
        FileChannel target = FileChannel.open(Paths.get("/Users/chenshaoping/test1.txt"), StandardOpenOption.APPEND, StandardOpenOption.WRITE);
        channel.transferTo(0, channel.size(), target);
        channel.close();

    }

    public static void main(String[] args) throws IOException {
        FileChannelTest test = new FileChannelTest();
        test.transfer();
    }
}
