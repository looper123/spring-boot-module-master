package com.quark.module;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2018/3/1/001.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ZookeerInfoTest {

    @Test
    public void zkConnTest() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper("192.168.194.130:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
            }
        });
        ZooKeeper.States zooKeeperState = zooKeeper.getState();
        System.out.println("---------" + zooKeeperState);
    }


}
