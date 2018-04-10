package com.quark.module.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by ZhenpengLu on 2018/4/4.
 * 自定义commandLineRunner  用于在afterRefresh（）中的callRunner（）
 * 自定义一些执行内容 如配置 、数据库连接。。
 *  note: 它不是通过
 *  @see org.springframework.core.io.support.SpringFactoriesLoader 加载的 所以不能通过spring.fatories配置方式实现
 */
@Component
//可以指定bean 初始化顺序
@Order(3)
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("MyCommandLineRunner is executing....");
    }
}
