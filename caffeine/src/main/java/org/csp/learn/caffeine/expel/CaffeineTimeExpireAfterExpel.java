package org.csp.learn.caffeine.expel;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import java.sql.Time;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * @author 陈少平
 * @date 2022-06-22 22:21
 */
public class CaffeineTimeExpireAfterExpel {

    public static void main(String[] args) throws InterruptedException {
        Cache<Object, Object> cache = Caffeine.newBuilder()
                .expireAfter(new Expiry<Object, Object>() {
                    @Override
                    public long expireAfterCreate(@NonNull Object key, @NonNull Object value, long currentTime) {
                        // 创建 3s 后过期
                        return TimeUnit.SECONDS.toNanos(3);
                    }

                    @Override
                    public long expireAfterUpdate(@NonNull Object key, @NonNull Object value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }

                    @Override
                    public long expireAfterRead(@NonNull Object key, @NonNull Object value, long currentTime, @NonNegative long currentDuration) {
                        return currentDuration;
                    }
                })
                .build();

        cache.put(1, 1);
        TimeUnit.SECONDS.sleep(2);
        //不为 null, 未到过期时间
        System.out.println(cache.getIfPresent(1));
        //为 null, 已到过期时间
        TimeUnit.SECONDS.sleep(1);
        System.out.println(cache.getIfPresent(1));


        cache.put(2, 2);
        TimeUnit.SECONDS.sleep(2);
        cache.put(2, 3);
        TimeUnit.SECONDS.sleep(2);
        // 为 null, 更新，不刷新过期时间
        System.out.println(cache.getIfPresent(2));
    }
}
