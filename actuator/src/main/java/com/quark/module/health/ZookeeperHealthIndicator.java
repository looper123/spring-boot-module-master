package com.quark.module.health;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/3/1/001.
 * 自定义zookeeper 连接健康检查器
 */
@Component
public class ZookeeperHealthIndicator extends AbstractHealthIndicator {

//    public ZookeeperHealthIndicator() {
//    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        ZooKeeper zooKeeper =  new ZooKeeper("192.168.194.130:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
            }
        });
        ZooKeeper.States connStates = zooKeeper.getState();
        builder.up().withDetail("zkConnectionStatus", connStates);
        zooKeeper.close();
    }

//
//    @Override
//    public Health health() {
//        ZooKeeper zooKeeper = null;
//        try {
//            zooKeeper = new ZooKeeper("192.168.194.130:2181", 5000, new Watcher() {
//                @Override
//                public void process(WatchedEvent event) {
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ZooKeeper.States connStates = zooKeeper.getState();
//        try {
//            zooKeeper.close();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//       return  Health.up().withDetail("zookeeper", connStates).build();
//    }
}
