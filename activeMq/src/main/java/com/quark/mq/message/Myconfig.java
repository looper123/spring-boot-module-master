package com.quark.mq.message;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Administrator on 2018/3/16/016.
 */
@Configuration
@ImportResource(locations ={"classpath:spring-jms-activemq.xml"} )
public class Myconfig {
}
