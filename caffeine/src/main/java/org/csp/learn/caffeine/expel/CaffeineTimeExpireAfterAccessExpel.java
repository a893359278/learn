package org.csp.learn.caffeine.expel;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;

/**
 * expireAfterAccess:最后一次访问(读或写)过期
 *
 * @author 陈少平
 * @date 2022-06-22 21:34
 */
public class CaffeineTimeExpireAfterAccessExpel {

    public static void main(String[] args) throws InterruptedException {
        // 自最后一次访问（读或写）过期
        Cache<Object, Object> cache1 = Caffeine.newBuilder()
                .expireAfterAccess(3, TimeUnit.SECONDS)
                .build();

        cache1.put(1, 1);
        cache1.getIfPresent(1);
        TimeUnit.SECONDS.sleep(3);
        // 结果为 null，写后 3s 过期
        System.out.println(cache1.getIfPresent(1));


        cache1.put(1, 1);
        TimeUnit.SECONDS.sleep(2);
        // 不为 null, 因为未到过期时间
        System.out.println(cache1.getIfPresent(1));

        TimeUnit.SECONDS.sleep(2);
        // 不为null,因为读操作，刷新了过期时间
        System.out.println(cache1.getIfPresent(1));

        cache1.put(1, 2);
        TimeUnit.SECONDS.sleep(2);
        // 不为null,因为写入，刷新 key 过期时间
        System.out.println(cache1.getIfPresent(1));

    }
}
