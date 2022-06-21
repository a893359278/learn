package org.csp.learn.caffeine.add;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 手动异步加载
 * @author 陈少平
 * @date 2022-06-21 22:38
 */
public class CaffeineManualAsyncLoad {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        AsyncCache<String, String> cache = Caffeine.newBuilder()
                .maximumSize(1024)
                .expireAfterWrite(Duration.ofSeconds(3L))
                .buildAsync();


        // 查找缓存原生，没有找到返回 null
        CompletableFuture<String> test = cache.getIfPresent("test");
        System.out.println(test);


        //查找缓存元素，如果不存在，异步生成
        CompletableFuture<String> future = cache.get("test", key -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程被唤醒了");
            return key;
        });
        System.out.println("未被阻塞");
        System.out.println(future.get());


        // 添加或更新一个元素
        String key = "test1";
        cache.put(key, CompletableFuture.supplyAsync(() -> {
            return key;
        }));
        // 需要判空
        future = cache.getIfPresent("test1");
        if (Objects.nonNull(future)) {
            System.out.println(future.get());
        }


        // 移除元素
        cache.synchronous().invalidate("test1");
    }
}
