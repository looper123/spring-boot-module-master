package com.quark.mq;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;
import javax.jms.Topic;

@SpringBootApplication
@EnableJms
public class ActiveMqApplication {

	/**
	 * 点对点模式
	 * @return
     */
	@Bean
	public Queue queue() {
		return new ActiveMQQueue("sample.queue");
	}

	/**
	 * 发布订阅模式
	 * @return
     */
	@Bean
	public Topic topic() {
		return new ActiveMQTopic("sample.topic");
	}




	public static void main(String[] args) {
		SpringApplication.run(ActiveMqApplication.class, args);
	}
}
