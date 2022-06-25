package org.csp.learn.caffeine.add.action;

import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * @author 陈少平
 * @date 2022-06-25 14:25
 */
public class MemoryCache {


    LoadingCache<String, KeyMapping> cache = Caffeine
            .newBuilder()
            .maximumSize(1024)
            .expireAfterWrite(30, TimeUnit.SECONDS)
            // 当写 1s 后，如果再次读时，则会刷新该 key
            .refreshAfterWrite(1, TimeUnit.SECONDS)
            .build(new CacheLoader<String, KeyMapping>() {

                // get(k) 方法，获取不到元素时，会调用该方法
                @Nullable
                @Override
                public KeyMapping load(@NonNull String key) throws Exception {
                    // 假设从数据库读取到对应的 key。
                    if (key.equals("hhh")) {
                        return null;
                    }

                    return new KeyMapping(key, key);
                }

                // getAll(keys) 方法，获取不到元素，会调用该方法
                @Override
                public @NonNull Map<String, KeyMapping> loadAll(@NonNull Iterable<? extends String> keys) throws Exception {

                    Map<String, KeyMapping> map = new HashMap<>();
                    // 批量获取 key 时，如果拿不到，会调用该方法
                    for (String key : keys) {
                        map.put(key, new KeyMapping(key, key));
                    }

                    return map;
                }

                // refresh(k)
                @Nullable
                @Override
                public KeyMapping reload(@NonNull String key, @NonNull KeyMapping oldValue) throws Exception {
                    System.out.println(String.format("reload key %s, oldValue %s", key, oldValue));
                    return oldValue;
                }
            });


    public static void main(String[] args) throws InterruptedException {
        MemoryCache memoryCache = new MemoryCache();
        LoadingCache<String, KeyMapping> cache = memoryCache.cache;

        System.out.println(cache.get("test"));
        System.out.println(cache.get("hhh"));

        List<String> keys = Arrays.asList("test1", "test2", "test3", "test4");
        Map<String, KeyMapping> all = cache.getAll(keys);
        System.out.println(JSON.toJSONString(all));

        cache.put("d1", new KeyMapping("d1", "d1"));
        TimeUnit.SECONDS.sleep(1);
        // 输出 reload key d1, oldValue MemoryCache.KeyMapping(key=d1, mapping=d2)
        // 因为，在 1s 读取后，会刷新该 key
        cache.get("d1");
        TimeUnit.SECONDS.sleep(1);


        cache.put("d2", new KeyMapping("d2", "d2"));
        TimeUnit.SECONDS.sleep(1);
        // 不输出 reload key，因为写操作，不会触发
        cache.put("d2", new KeyMapping("d3", "d3"));
        TimeUnit.SECONDS.sleep(1);
    }

    @Data
    @AllArgsConstructor
    public static class KeyMapping {
        private String key;
        private String mapping;
    }

}
