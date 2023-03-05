package org.csp.learn.zk;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

/**
 * @author 陈少平
 * @date 2023-02-18 14:08
 */
public class CrudAPI {

    private static ZooKeeper zkClient;
    private static ZKWatcher watcher;
    private static ZKConnectedWatcher2 watcher2;
    /**
     * 初始化 zk 客户端.
     *
     * zk 连接是异步动作.
     */
    @Before
    public void init() {
        try {
            watcher = new ZKWatcher();
            watcher2 = new ZKConnectedWatcher2();
            // 异步
            zkClient = new ZooKeeper("localhost:2181", 5000, watcher);
            ZKWatcher.countDownLatch.await();
            System.out.println("connect zk success ...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void connectZk() {
        if (zkClient == null) {
            System.out.println("111");
        }
    }

    public static class ZKWatcher implements Watcher {

        public static CountDownLatch countDownLatch = new CountDownLatch(1);

        /**
         * 连接创建成功的回调
         * @param watchedEvent
         */
        @Override
        public void process(WatchedEvent watchedEvent) {
            try {
                System.out.println("receiving watch event: " + watchedEvent);
                if (watchedEvent.getState() == KeeperState.SyncConnected) {
                    countDownLatch.countDown();
                }
            } catch (Exception e)  {
            }
        }
    }

    public static class ZKConnectedWatcher2 implements Watcher {

        @Override
        public void process(WatchedEvent watchedEvent) {
            try {
                if (watchedEvent.getType() == EventType.NodeChildrenChanged) {
                    System.out.println("ReGet child: " + zkClient.getChildren(watchedEvent.getPath(), true));
                }
            } catch (Exception e) {
            }

        }
    }


    public static class IChildrenCallback implements AsyncCallback.Children2Callback {

        @Override
        public void processResult(int retCode, String path, Object ctx, List<String> children, Stat stat) {
            System.out.println("retCode :" + retCode + " path: " + path + " ctx: " + ctx + " children: " + JSON.toJSONString(children) + " stat: " + JSON.toJSONString(stat));
        }
    }

    /**
     *
     */
    @Test
    public void getChildren_01() {
        try {
            /**
             * watcher2: 用于监听后续子节点数目是否有变化. 如果子节点的子节点变化，不会通知
             */
            zkClient.getChildren("/test", watcher2, new IChildrenCallback(), null);
            TimeUnit.SECONDS.sleep(60);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void acl() throws KeeperException, InterruptedException, IOException {
        zkClient.addAuthInfo("digest", "foo:true".getBytes());
        zkClient.create("/hello", "zzz".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);

        ZooKeeper zkClient2 = new ZooKeeper("localhost:2181", 5000, null);
        zkClient2.delete("/hello", -1);
    }
}
