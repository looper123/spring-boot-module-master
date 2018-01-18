package com.quark.module;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootModuleMasterApplicationTests {

	@Resource(name = "redisTemplate")
	private RedisTemplate redisTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void redisSentinelTest(){
		redisTemplate.opsForValue().set("sentinel-v1","sentinel-v1");
		Object sentinel = redisTemplate.opsForValue().get("sentinel-v1");
		System.out.println(sentinel+"-----------------");
	}

}
