package com.quark.module;


import com.quark.module.entity.User;
import com.quark.module.pool.JedisSentinelShardedPool;
import com.quark.module.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootModuleMasterApplicationTests {

	@Resource(name = "myRedisTemplate")
	private RedisTemplate redisTemplate;

	@Resource(name="sentinelConfig")
	private RedisSentinelConfiguration sentinelConfig;


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StringRedisSerializer keySerializer;

	@Autowired
	private GenericJackson2JsonRedisSerializer valueSerializer;

	private final static String KEY_PREFIX = "app_key_";


	@Test
	public void contextLoads() {
	}

	@Test
	public void redisSentinelTest(){
		for (int i = 0; i < 20; i++) {
			redisTemplate.opsForValue().set("sentinel-v"+i,"sentinel-v"+i);
		}

//		Object sentinel = redisTemplate.opsForValue().get("sentinel-v1");
//		redisTemplate.opsForValue().set("app_key_1","111");
//		System.out.println(sentinel+"-----------------");
	}


	@Test
	public void multiOperation(){
		User user = userRepository.findByNameAndId("looper",1l);
		redisTemplate.opsForValue().set(KEY_PREFIX+user.getId(),user);

	}


	@Test
	public void jpaTest(){
		Iterable<User> all = userRepository.findAll();
		System.out.println("end of find.....");
	}


	//对redisSentinelConfig 进行分片
	@Test
	public void shardTest(){
		JedisSentinelShardedPool pool = createPool(this.sentinelConfig);
		ShardedJedis resource = pool.getResource();
		List<String>  list = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			list.add(resource.get("sentinel-v"+i));
		}
		System.out.println("11111");
	}
	private JedisSentinelShardedPool createPool(RedisSentinelConfiguration config) {
		return  new JedisSentinelShardedPool(config.getMaster().getName(), convertToJedisSentinelSet(config.getSentinels()),
				new JedisPoolConfig(), 3000,
				3000,null, Protocol.DEFAULT_DATABASE, null);
	}
	private int getTimeoutFrom(JedisShardInfo shardInfo) {
		return (Integer) ReflectionUtils.invokeMethod( ReflectionUtils.findMethod(JedisShardInfo.class, "getTimeout"), shardInfo);
	}
	private Set<String> convertToJedisSentinelSet(Collection<RedisNode> nodes) {
		if (CollectionUtils.isEmpty(nodes)) {
			return Collections.emptySet();
		}
		Set<String> convertedNodes = new LinkedHashSet<String>(nodes.size());
		for (RedisNode node : nodes) {
			if (node != null) {
				convertedNodes.add(node.asString());
			}
		}
		return convertedNodes;
	}


}
