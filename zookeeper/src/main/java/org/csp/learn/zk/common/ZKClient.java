package org.csp.learn.zk.common;

import static org.apache.zookeeper.KeeperException.Code.NODEEXISTS;

import java.util.List;
import java.util.concurrent.locks.LockSupport;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * 封装好基本操作
 *
 * @author 陈少平
 * @date 2023-02-18 16:07
 */
public class ZKClient {

    private static ZooKeeper zkClient;

    private ZKClient() {
        init();
    }

    private void init() {
        zkClient = createClient();
    }

    private ZooKeeper createClient() {
        Thread thread = Thread.currentThread();
        ZooKeeper keeper = null;
        try {
            keeper = new ZooKeeper("localhost:2181", 30000, event -> {
                System.out.println("receive zk callback... " + event);
                if (event.getState() == KeeperState.SyncConnected) {
                    // 证明是初次连接
                    if (event.getPath() == null) {
                        System.out.println("Connecting zk success.");
                        LockSupport.unpark(thread);
                    }
                }
            });
            LockSupport.park(thread);
            return keeper;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (keeper == null) {
            throw new RuntimeException("Connecting zk fail.");
        }
        return keeper;
    }

    public ZooKeeper getClient() {
        return zkClient;
    }

    public static class ZKClientFactory {

        private static ZKClient myZKClient = new ZKClient();

        private ZKClientFactory() {
            myZKClient = new ZKClient();
        }

        public static ZKClient getInstance() {
            return myZKClient;
        }
    }

    public Stat exists(String path, Watcher watcher) {
        try {
            return zkClient.exists(path, watcher);
        } catch (Exception e) {
        }
        return null;
    }

    public void create(String path) {
        try {
            zkClient.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            if (e.code() == NODEEXISTS) {
                return;
            }
            throw new RuntimeException(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<String> getChildren(String path, Watcher watcher) {
        try {
            return zkClient.getChildren(path, watcher);
        } catch (Exception e) {
        }
        return null;
    }

    public String getData(String path, Watcher watcher, Stat stat) {
        try {
            byte[] data = zkClient.getData(path, watcher, stat);
            return null != data ? new String(data) : null;
        } catch (Exception e) {
        }
        return null;
    }


}


