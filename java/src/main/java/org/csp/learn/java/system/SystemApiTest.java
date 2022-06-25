package org.csp.learn.java.system;

/**
 * @author 陈少平
 * @date 2022-06-25 19:27
 */
public class SystemApiTest {

    public static void main(String[] args) {
        SystemApiTest test = new SystemApiTest();
//        test.nanoCompareCtm();
        test.nanoTest();
        test.currentTimeTest();
    }

    /**
     *测试 System.nanoTime() 和 System.currentTimeMillis() 方法.
     * nanoTime 主要用于测量一段代码的执行时间
     *
     */
    public void nanoCompareCtm() {
        System.out.println(System.nanoTime());
        System.out.println(System.nanoTime());
        System.out.println(System.currentTimeMillis());
    }

    public void nanoTest() {
        long startTime = System.nanoTime();

        Long sum = 0L;
        for (int i = 0; i < 1000000; i++) {
            sum += i;
        }

        System.out.println(System.nanoTime() - startTime);
    }

    public void currentTimeTest() {
        long startTime = System.currentTimeMillis();

        Long sum = 0L;
        for (int i = 0; i < 1000000; i++) {
            sum += i;
        }

        System.out.println(System.currentTimeMillis() - startTime);
    }
}
