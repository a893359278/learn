package org.csp.learn.caffeine.policy;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * @author 陈少平
 * @date 2022-06-25 16:46
 */
public class DanamicPolicy {

    public static void main(String[] args) throws InterruptedException {
        Cache<String, String> cache = Caffeine.newBuilder()
//                .maximumWeight(1024L)
//                .weigher((key, value) -> value.toString().length())
                .maximumSize(1024)
                .build();

        for (int i = 0; i < 2000; i++) {
            cache.put(i+"", i+"");
        }
        Thread.sleep(1000);
        System.out.println(cache.getIfPresent("0"));
        cache.policy().eviction().ifPresent(eviction -> {
            eviction.setMaximum(2048);
        });
        for (int i = 0; i < 2000; i++) {
            cache.put(i+"", i+"");
        }
        Thread.sleep(1000);
        System.out.println(cache.getIfPresent("2"));
    }
}
