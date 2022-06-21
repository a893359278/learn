package org.csp.learn.caffeine.add;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 自动同步加载
 * @author 陈少平
 * @date 2022-06-21 22:15
 */
public class CaffeineAutomaticSyncLoad {

    public static void main(String[] args) {
        LoadingCache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(1024)
                .expireAfterWrite(Duration.ofSeconds(30L))
                .build(key -> asyncCreatorValue(key));

        // 查找缓存, 如果找不到，则调用 asyncCreatorValue 方法，加载缓存. 如果无法生成，返回 null
        String v = cache.get("test");
        System.out.println(v);

        // 同理，不过是个批量接口
        List<String> keys = new ArrayList<>();
        keys.add("test1");
        keys.add("test2");
        keys.add("test3");
        Map<String, String> all = cache.getAll(keys);
        for (Entry<String, String> entry : all.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    private static String asyncCreatorValue(String key) {
        return key;
    }
}
