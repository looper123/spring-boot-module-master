package com.quark.module.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by ZhenpengLu on 2018/4/4.
 * 自定义ApplicationContextInitializer  在容器准备完成前对
 * @see ConfigurableApplicationContext 的进一步配置&处理
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("a further handing and configuration to ConfigurableApplicationContext.....");
    }
}
