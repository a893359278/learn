package org.csp.learn.caffeine.expel;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 基于最大数量驱逐
 * @author 陈少平
 * @date 2022-06-22 21:12
 */
public class CaffeineSizeMaximumExpel {

    public static void main(String[] args) {
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(1024)
                .build();
    }
}
