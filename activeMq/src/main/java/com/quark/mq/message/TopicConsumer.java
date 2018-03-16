package com.quark.mq.message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/3/16/016.
 * 接收topic的消息 （发布订阅模式）
 */
@Component
public class TopicConsumer {

    @JmsListener(destination = "sample.topic")
    public void receiveTopic(String text) {
        System.out.println("consuming  from topic"+text+"....");
    }
}
