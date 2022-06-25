package org.csp.learn.caffeine.expel;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * 基于引用
 * @author 陈少平
 * @date 2022-06-22 23:11
 */
public class CaffeineReferenceExpel {

    public static void main(String[] args) {
        // 弱引用
        Cache<Object, Object> cache1 = Caffeine.newBuilder()
                .weakKeys()
                .weakValues()
                .build();

        String s1 = new String("1");

        cache1.put(s1, "s2");

        String s2 = new String("1");
        // null，基于 == 号进行比较，而不是 equals()
        System.out.println(cache1.getIfPresent(s2));



        // 软引用
        Cache<Object, Object> cache2 = Caffeine.newBuilder()
                .softValues()
                .build();
        cache2.put(s1, "s2");
        System.out.println(cache2.getIfPresent(s2));
    }
}
