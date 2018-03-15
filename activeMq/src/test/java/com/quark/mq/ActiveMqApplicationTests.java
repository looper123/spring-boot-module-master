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

	@Test
	public void mqConnectionTest() throws InterruptedException {
		this.producer.send("Test message");
		Thread.sleep(1000L);
//		assertThat(this.outputCapture.toString().contains("Test message")).isTrue();
	}
}
