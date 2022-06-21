package org.csp.learn.caffeine.add;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈少平
 * @date 2022-06-21 22:59
 */
public class CaffeineAutomaticAsyncLoad {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AsyncLoadingCache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                // 你可以选择: 去异步的封装一段同步操作来生成缓存元素
                .buildAsync(key -> asyncCreatorValue(key));
                // 你也可以选择: 构建一个异步缓存元素操作并返回一个future
                // .buildAsync((key, executor) -> createExpensiveGraphAsync(key, executor));

        // 查找缓存元素，如果其不存在，将会异步进行生成
        CompletableFuture<String> graph = cache.get("test1");
        System.out.println(graph.get());

        // 批量查找缓存元素，如果其不存在，将会异步进行生成
        List<String> keys = new ArrayList<>();
        keys.add("test1");
        keys.add("test2");
        keys.add("test3");
        CompletableFuture<Map<String, String>> graphs = cache.getAll(keys);
        Map<String, String> all = graphs.get();
        for (Entry<String, String> entry : all.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    private static String asyncCreatorValue(String key) {
        return key;
    }
}
