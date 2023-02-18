package org.csp.learn.zk.configserver;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.data.Stat;
import org.csp.learn.zk.common.ZKClient;
import org.csp.learn.zk.common.ZKClient.ZKClientFactory;

/**
 * @author 陈少平
 * @date 2023-02-18 16:18
 */
public class Bootstrap {

    private ZKClient zkClient;
    private String configPath = "/config";
    private List<String> configKeyList;
    private Map<String, String> configMap = new HashMap<>();
    private MyWatch myWatch = new MyWatch();

    public Bootstrap() {
        // 1. 拿到 ZKClient
        zkClient = ZKClientFactory.getInstance();

        // 2. 判断节点存不存在
        Stat stat = zkClient.exists(configPath, null);
        if (null == stat) {
            zkClient.create(configPath);
        }

        // 3. 获取子节点列表，并监听
        configKeyList = zkClient.getChildren(configPath, myWatch);

        // 4. 调用 getData api 监听数据变更
        if (configKeyList != null && !configKeyList.isEmpty()) {
            configKeyList.forEach(key -> {
                String data = zkClient.getData(this.fullPath(key), myWatch, null);
                configMap.put(key, data);
            });
            printNowData();
        }
    }

    public class MyWatch implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            System.out.println("receive mywatch ..." + event);
            try {
                if (event.getType() == EventType.NodeChildrenChanged) {
                    List<String> newConfigKeys = Bootstrap.this.zkClient.getChildren(configPath, this);
                    // 对比判断，哪个是新增
                    List<String> newKeys = new ArrayList<>();
                    newConfigKeys.forEach(newKey -> {
                        if (!configKeyList.contains(newKey)) {
                            newKeys.add(newKey);
                        }
                    });

                    configKeyList = newConfigKeys;

                    // 对新 key 进行 watch
                    newKeys.forEach(newKey -> {
                        String data = zkClient.getData(Bootstrap.this.fullPath(newKey), this, null);
                        configMap.put(newKey, data);
                    });

                } else if (event.getType() == EventType.NodeDataChanged) {
                    String data = zkClient.getData(event.getPath(), this, null);
                    String path = event.getPath();
                    configMap.put(path.substring(path.lastIndexOf("/") + 1), data);
                    printNowData();
                } else if (event.getType() == EventType.NodeDeleted) {
                    configMap.remove(getSubNodePath(event.getPath()));
                    printNowData();
                }

            } catch (Exception e) {
            }
        }
    }

    public String getSubNodePath(String path) {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public String fullPath(String path) {
        return configPath + "/" + path;
    }

    private void printNowData() {
        System.out.println(JSON.toJSONString(configMap));
    }
}
