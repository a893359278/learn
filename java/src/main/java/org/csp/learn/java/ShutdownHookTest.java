package org.csp.learn.java;

import java.io.IOException;

public class ShutdownHookTest {

    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("exit...");
        }));

        System.in.read();
    }
}
