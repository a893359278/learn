package org.csp.learn.serialization.constant;

/**
 * @author 陈少平
 * @date 2023-05-18 23:21
 */
public enum OperatorEnum {
    ADD("+") {
        @Override
        int calculate(int a, int b) {
            return a + b;
        }
    },
    SUBTRACT("-") {
        @Override
        int calculate(int a, int b) {
            return a - b;
        }
    };
    private String operator;
    OperatorEnum(String operator) {
        this.operator = operator;
    }
    // 定义个了抽象方法，用于每一个枚举去实现
    abstract int calculate(int a, int b);
}
