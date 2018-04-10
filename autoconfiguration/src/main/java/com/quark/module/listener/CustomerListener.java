package com.quark.module.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by ZhenpengLu on 2018/4/3.
 * spring.factories 内置加载机制 实现spring 容器初始化过程的监听
 */
public class CustomerListener implements SpringApplicationRunListener{

    private final SpringApplication application;
    private final String[] args;
    /**
     * 构造中必须存在这个两个成员 否则会报错 noSuchMethod exception  找不到CustomerListener的init方法
     *  @see org.springframework.boot.context.event.EventPublishingRunListener
     */

    public CustomerListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void starting() {
        System.out.println("run方法执行");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("环境准备完成");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("容器准备完成");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("容器加载");
    }

    @Override
    public void finished(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("容器初始化完成");
    }
}
