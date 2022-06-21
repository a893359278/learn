package org.csp.learn.caffeine.add;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * 手动同步加载
 * @author 陈少平
 * @date 2022-06-21 21:22
 */
public class CaffeineManualSyncLoad {

    public static void main(String[] args) throws InterruptedException {
        Cache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(1024)
                .expireAfterWrite(Duration.ofSeconds(3L))
                .build();

        // 查找缓存元素，没有找到返回 null
        String v = cache.getIfPresent("test");
        System.out.println(v);

        // 查找缓存, 如果缓存不存在，则生成缓存元素，如果无法生成，则返回 null
        String test = cache.get("test", k -> {
            return k;
        });
        System.out.println(test);

        // 添加或更新一个元素
        cache.put("test", "v");
        v = cache.getIfPresent("test");
        System.out.println(v);

        // 移出元素
        cache.invalidate("test");
        v = cache.getIfPresent("test");
        System.out.println(v);

        // 使用 cache.asMap 暴露出来的 Map，和正常使用 cache 是一样的，key 同样会过期
        ConcurrentMap<String, String> map = cache.asMap();
        map.put("test2", "test2");
        TimeUnit.SECONDS.sleep(2);
        v = cache.getIfPresent("test2");
        System.out.println("get from cache " + v);
        String test2 = map.get("test2");
        System.out.println("get from cmp " + test2);
    }

}
