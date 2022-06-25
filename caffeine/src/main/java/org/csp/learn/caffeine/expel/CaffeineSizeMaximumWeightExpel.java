package org.csp.learn.caffeine.expel;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 基于权重的驱逐
 * @author 陈少平
 * @date 2022-06-22 21:20
 */
public class CaffeineSizeMaximumWeightExpel {

    public static void main(String[] args) {
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumWeight(10)
                // 基于权重的，必须实现该函数
                .weigher((String k, String v) -> v.length())
                .build();
    }
}
