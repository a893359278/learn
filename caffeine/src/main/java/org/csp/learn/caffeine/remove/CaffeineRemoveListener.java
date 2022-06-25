package org.csp.learn.caffeine.remove;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈少平
 * @date 2022-06-25 13:36
 */
public class CaffeineRemoveListener {

    public static void main(String[] args) throws InterruptedException {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .maximumSize(3)
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .removalListener((k, v, cause) -> {
                    System.out.println(String.format("current thread %s, key %s, cause %s", Thread.currentThread(), k, cause.toString()));
                })
                .build();

        cache.put(1, 1);
        // 手动剔除
        // current thread Thread[ForkJoinPool.commonPool-worker-1,5,main], key 1, cause EXPLICIT
        cache.invalidate(1);
        cache.put(2, 2);
        TimeUnit.SECONDS.sleep(3);
        // 被动过期 current thread Thread[ForkJoinPool.commonPool-worker-1,5,main], key 2, cause EXPIRED
        cache.getIfPresent(2);
        TimeUnit.SECONDS.sleep(1);
        cache.put(3, 3);
        cache.put(4, 4);
        cache.put(5, 5);
        // current thread Thread[ForkJoinPool.commonPool-worker-2,5,main], key 3, cause SIZE
        cache.put(6, 6);
    }
}
