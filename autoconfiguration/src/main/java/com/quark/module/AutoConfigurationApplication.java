package com.quark.module;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoConfigurationApplication {

	final static Logger logger = LoggerFactory.getLogger(AutoConfigurationApplication.class);

	public static void main(String[] args) {
		logger.info("获取logback 内部状态....");
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//		打印logback内部状态数据
		StatusPrinter.print(lc);
		SpringApplication.run(AutoConfigurationApplication.class, args);
	}
}
