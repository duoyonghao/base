package com.kqds.util.sys.other;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.kqds.core.global.YZSysProps;
import com.kqds.util.sys.YZUtility;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis工具类
 * 
 * @author Administrator
 * 
 */
public class JedisUtil {
	
	// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static int MAX_ACTIVE = 1024;
	// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static int MAX_IDLE = 200;
	// 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static int MAX_WAIT = 10000;

	// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static boolean TEST_ON_BORROW = true;
	private static JedisPool jedisPool = null;
	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			String confFile = YZSysProps.getWebInfPath() + File.separator + "config" + File.separator + "redis.properties";
			FileInputStream is = new FileInputStream(confFile);// 属性文件流
			Properties dbproperties = new Properties();
			dbproperties.load(is);
			JedisPoolConfig config = new JedisPoolConfig();

			String ip = YZUtility.isNullorEmpty(dbproperties.getProperty("ip")) ? "127.0.0.1" : dbproperties.getProperty("ip");
			String port = YZUtility.isNullorEmpty(dbproperties.getProperty("port")) ? "6379" : dbproperties.getProperty("port");

			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config,ip, Integer.valueOf(port), MAX_WAIT, dbproperties.getProperty("pass"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	@SuppressWarnings("deprecation")
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.close();
		}
	}
}