package com.quark.module.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Administrator on 2018/1/18/018.
 */
@Configuration
@ImportResource(locations = {"classpath:spring-redis.xml"})
public class MyConfig {
}

