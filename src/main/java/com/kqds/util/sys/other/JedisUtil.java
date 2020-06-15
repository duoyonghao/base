package com.kqds.util.sys.other;

import com.kqds.core.global.YZSysProps;
import com.kqds.util.sys.YZUtility;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
  private static int MAX_ACTIVE = 1024;
  
  private static int MAX_IDLE = 200;
  
  private static int MAX_WAIT = 10000;
  
  private static boolean TEST_ON_BORROW = true;
  
  private static JedisPool jedisPool = null;
  
  static {
    try {
      String confFile = String.valueOf(YZSysProps.getWebInfPath()) + File.separator + "config" + File.separator + "redis.properties";
      FileInputStream is = new FileInputStream(confFile);
      Properties dbproperties = new Properties();
      dbproperties.load(is);
      JedisPoolConfig config = new JedisPoolConfig();
      String ip = YZUtility.isNullorEmpty(dbproperties.getProperty("ip")) ? "127.0.0.1" : dbproperties.getProperty("ip");
      String port = YZUtility.isNullorEmpty(dbproperties.getProperty("port")) ? "6379" : dbproperties.getProperty("port");
      config.setMaxTotal(MAX_ACTIVE);
      config.setMaxIdle(MAX_IDLE);
      config.setTestOnBorrow(TEST_ON_BORROW);
      jedisPool = new JedisPool((GenericObjectPoolConfig)config, ip, Integer.valueOf(port).intValue(), MAX_WAIT, dbproperties.getProperty("pass"));
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static synchronized Jedis getJedis() {
    try {
      if (jedisPool != null) {
        Jedis resource = jedisPool.getResource();
        return resource;
      } 
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public static void returnResource(Jedis jedis) {
    if (jedis != null)
      jedisPool.close(); 
  }
}
