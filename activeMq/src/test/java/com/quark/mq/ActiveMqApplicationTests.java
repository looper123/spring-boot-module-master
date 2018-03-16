package com.quark.mq;

import com.quark.mq.message.Producer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActiveMqApplicationTests {

	@Autowired
	private Producer producer;

	/**
	 * 用来捕捉打印的信息
	 */
	@Rule
	public OutputCapture outputCapture = new OutputCapture();


	@Test
	public void contextLoads() {
	}

	/**
	 * 测试点对点模式
	 * @throws InterruptedException
     */
	@Test
	public void pointToPointModelTest() throws InterruptedException {
		this.producer.sendQueue("queue message");
		Thread.sleep(1000L);
//		assertThat(this.outputCapture.toString().contains("Test message")).isTrue();
	}

	@Test
	public void pubSubModelTest() throws InterruptedException {
		this.producer.sendTopic("topic message");
		Thread.sleep(1000L);
//		assertThat(this.outputCapture.toString().contains("Test message")).isTrue();
	}
}
