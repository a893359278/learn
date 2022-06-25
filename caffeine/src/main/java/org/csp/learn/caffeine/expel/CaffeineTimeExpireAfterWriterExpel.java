package org.csp.learn.caffeine.expel;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author 陈少平
 * @date 2022-06-22 21:52
 */
public class CaffeineTimeExpireAfterWriterExpel {

    public static void main(String[] args) throws InterruptedException {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .build();

        cache.put(1, 2);
        TimeUnit.SECONDS.sleep(2);
        // 不为null, 未到过期时间
        System.out.println(cache.getIfPresent(1));

        TimeUnit.SECONDS.sleep(1);
        // 为null, 自最后一次写入，已过 3s
        System.out.println(cache.getIfPresent(1));

        cache.put(1, 3);
        TimeUnit.SECONDS.sleep(2);
        cache.put(1, 3);
        TimeUnit.SECONDS.sleep(2);
        // 不为 null
        System.out.println(cache.getIfPresent(1));
    }
}
