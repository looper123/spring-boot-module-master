package com.quark.mq.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Queue;
import javax.jms.Topic;

@Component
public class Producer implements CommandLineRunner {

//	@Autowired
//	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	private JmsTemplate queueJmsTemplate;

	@Autowired
	private JmsTemplate topicJmsTemplate;

//	@Autowired
//	private JmsTemplate jmsTemplate;

	@Autowired
	private Queue queue;

	@Autowired
	private Topic topic;

	@Override
	public void run(String... args) throws Exception {
//		send("other message");
//		System.out.println("Message was sent to the Queue");
	}

	/**
	 * 发送到queue （点对点模式  只有一个消费者能获取消息 --- 消息竞争）
	 * @param msg
     */
	public void sendQueue(String msg) {
//		this.jmsMessagingTemplate.convertAndSend(this.queue, msg);
		this.queueJmsTemplate.convertAndSend(this.queue, msg);
	}

	/**
	 * 发送消息到queue  （发布订阅模式  所有订阅该topic的 消费者都能获取消息）
	 * @param msg
     */
	public void sendTopic(String msg) {
//		jmsTemplate.setPubSubDomain(true);
//		jmsTemplate.setPubSubNoLocal(false);
//		this.jmsMessagingTemplate.convertAndSend(this.topic, msg);
		this.topicJmsTemplate.convertAndSend(this.topic, msg);
	}

}
