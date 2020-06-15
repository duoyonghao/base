package com.kqds.util.sys.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.query.SortQueryBuilder;

public class CacheUtil {
  private static final Logger LOG = LoggerFactory.getLogger(CacheUtil.class);
  
  private static RedisTemplate redisTemplate = CacheContextUtil.<RedisTemplate>getBean("redisTemplate", RedisTemplate.class);
  
  private static StringRedisTemplate stringRedisTemplate = CacheContextUtil.<StringRedisTemplate>getBean("stringRedisTemplate", StringRedisTemplate.class);
  
  private static String CACHE_PREFIX;
  
  private static boolean CACHE_CLOSED;
  
  private static boolean isEmpty(Object obj) {
    if (obj == null)
      return true; 
    if (obj instanceof String) {
      String str = obj.toString();
      if ("".equals(str.trim()))
        return true; 
      return false;
    } 
    if (obj instanceof List) {
      List<Object> list = (List<Object>)obj;
      if (list.isEmpty())
        return true; 
      return false;
    } 
    if (obj instanceof Map) {
      Map map = (Map)obj;
      if (map.isEmpty())
        return true; 
      return false;
    } 
    if (obj instanceof Set) {
      Set set = (Set)obj;
      if (set.isEmpty())
        return true; 
      return false;
    } 
    if (obj instanceof Object[]) {
      Object[] objs = (Object[])obj;
      if (objs.length <= 0)
        return true; 
      return false;
    } 
    return false;
  }
  
  private static String buildKey(String key) {
    if (CACHE_PREFIX == null || "".equals(CACHE_PREFIX))
      return key; 
    return String.valueOf(CACHE_PREFIX) + ":" + key;
  }
  
  public static String getCachePrefix() {
    return CACHE_PREFIX;
  }
  
  public static void setCachePrefix(String cachePrefix) {
    if (cachePrefix != null && !"".equals(cachePrefix.trim()))
      CACHE_PREFIX = cachePrefix.trim(); 
  }
  
  public static boolean close() {
    LOG.debug(" cache closed ! ");
    CACHE_CLOSED = true;
    return true;
  }
  
  public static boolean openCache() {
    CACHE_CLOSED = false;
    return true;
  }
  
  public static boolean isClose() {
    return CACHE_CLOSED;
  }
  
  public static boolean hasKey(String key) {
    LOG.debug(" hasKey key :{}", key);
    try {
      if (isClose() || isEmpty(key))
        return false; 
      key = buildKey(key);
      return redisTemplate.hasKey(key).booleanValue();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static Set<String> keys(String patternKey) {
    LOG.debug(" keys key :{}", patternKey);
    try {
      if (isClose() || isEmpty(patternKey))
        return Collections.emptySet(); 
      return redisTemplate.keys(patternKey);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return Collections.emptySet();
    } 
  }
  
  public static boolean del(String... key) {
    LOG.debug(" delete key :{}", key.toString());
    try {
      if (isClose() || isEmpty(key))
        return false; 
      Set<String> keySet = new HashSet<>();
      byte b;
      int i;
      String[] arrayOfString;
      for (i = (arrayOfString = key).length, b = 0; b < i; ) {
        String str = arrayOfString[b];
        keySet.add(buildKey(str));
        b++;
      } 
      redisTemplate.delete(keySet);
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static boolean delPattern(String key) {
    LOG.debug(" delete Pattern keys :{}", key);
    try {
      if (isClose() || isEmpty(key))
        return false; 
      key = buildKey(key);
      redisTemplate.delete(redisTemplate.keys(key));
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static boolean del(Set<String> keys) {
    LOG.debug(" delete keys :{}", keys.toString());
    try {
      if (isClose() || isEmpty(keys))
        return false; 
      Set<String> keySet = new HashSet<>();
      for (String str : keys)
        keySet.add(buildKey(str)); 
      redisTemplate.delete(keySet);
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static boolean setExp(String key, long seconds) {
    LOG.debug(" setExp key :{}, seconds: {}", key, Long.valueOf(seconds));
    try {
      if (isClose() || isEmpty(key) || seconds > 0L)
        return false; 
      key = buildKey(key);
      return redisTemplate.expire(key, seconds, TimeUnit.SECONDS).booleanValue();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static Long getExpire(String key) {
    LOG.debug(" getExpire key :{}", key);
    try {
      if (isClose() || isEmpty(key))
        return Long.valueOf(0L); 
      key = buildKey(key);
      return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return Long.valueOf(0L);
    } 
  }
  
  public static boolean setString(String key, String value) {
    LOG.debug(" setString key :{}, value: {}", key, value);
    try {
      if (isClose() || isEmpty(key) || isEmpty(value))
        return false; 
      key = buildKey(key);
      stringRedisTemplate.opsForValue().set(key, value);
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static boolean setString(String key, String value, long seconds) {
    LOG.debug(" setString key :{}, value: {}, timeout:{}", key, value);
    try {
      if (isClose() || isEmpty(key) || isEmpty(value))
        return false; 
      key = buildKey(key);
      stringRedisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static String getString(String key) {
    LOG.debug(" getString key :{}", key);
    try {
      if (isClose() || isEmpty(key))
        return null; 
      key = buildKey(key);
      return (String)stringRedisTemplate.opsForValue().get(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return null;
    } 
  }
  
  public static long incr(String key) {
    LOG.debug(" incr key :{}", key);
    try {
      if (isClose() || isEmpty(key))
        return 0L; 
      key = buildKey(key);
      return redisTemplate.opsForValue().increment(key, 1L).longValue();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return 0L;
    } 
  }
  
  public static boolean set(String key, Object obj) {
    LOG.debug(" set key :{}, value:{}", key, obj);
    try {
      if (isClose() || isEmpty(key) || isEmpty(obj))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForValue().set(key, obj);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } 
    return false;
  }
  
  public static boolean setObj(String key, Object obj, long seconds) {
    LOG.debug(" set key :{}, value:{}, seconds:{}", key, obj);
    try {
      if (isClose() || isEmpty(key) || isEmpty(obj))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForValue().set(key, obj);
      if (seconds > 0L)
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS); 
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static <T> T getObj(String key, Class<T> clazz) {
    LOG.debug(" get key :{}", key);
    try {
      if (isClose() || isEmpty(key))
        return null; 
      key = buildKey(key);
      return (T)redisTemplate.opsForValue().get(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return null;
    } 
  }
  
  public static <T> boolean setMap(String key, Map<String, T> map) {
    try {
      if (isClose() || isEmpty(key) || isEmpty(map))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForHash().putAll(key, map);
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static Map getMap(String key) {
    LOG.debug(" getMap key :{}", key);
    try {
      if (isClose() || isEmpty(key))
        return null; 
      key = buildKey(key);
      return redisTemplate.opsForHash().entries(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return null;
    } 
  }
  
  public static long getMapSize(String key) {
    LOG.debug(" getMap key :{}", key);
    try {
      if (isClose() || isEmpty(key))
        return 0L; 
      key = buildKey(key);
      return redisTemplate.opsForHash().size(key).longValue();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return 0L;
    } 
  }
  
  public static Object getMapKey(String key, String hashKey) {
    LOG.debug(" getMapkey :{}, hashKey:{}", key, hashKey);
    try {
      if (isClose() || isEmpty(key) || isEmpty(hashKey))
        return null; 
      key = buildKey(key);
      return redisTemplate.opsForHash().get(key, hashKey);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return null;
    } 
  }
  
  public static Set<Object> getMapKeys(String key) {
    LOG.debug(" getMapKeys key :{}", key);
    try {
      if (isClose() || isEmpty(key))
        return null; 
      key = buildKey(key);
      return redisTemplate.opsForHash().keys(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return null;
    } 
  }
  
  public static boolean delMapKey(String key, String hashKey) {
    LOG.debug(" delMapKey key :{}, hashKey:{}", key, hashKey);
    try {
      if (isClose() || isEmpty(key) || isEmpty(hashKey))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForHash().delete(key, new Object[] { hashKey });
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static <T> boolean setMapExp(String key, Map<String, T> map, long seconds) {
    LOG.debug(" setMapExp key :{}, value: {}, seconds:{}", key, map);
    try {
      if (isClose() || isEmpty(key) || isEmpty(map))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForHash().putAll(key, map);
      redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static <T> boolean addMap(String key, String hashKey, T value) {
    LOG.debug(" addMap key :{}, hashKey: {}, value:{}", key, hashKey);
    try {
      if (isClose() || isEmpty(key) || isEmpty(hashKey) || isEmpty(value))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForHash().put(key, hashKey, value);
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static <T> boolean setList(String key, List<T> list) {
    LOG.debug(" setList key :{}, list: {}", key, list);
    try {
      if (isClose() || isEmpty(key) || isEmpty(list))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForList().leftPushAll(key, list.toArray());
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } 
    return false;
  }
  
  public static Long listSize(String key) {
    try {
      if (isClose() || isEmpty(key))
        return Long.valueOf(0L); 
      key = buildKey(key);
      return redisTemplate.opsForList().size(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return Long.valueOf(0L);
    } 
  }
  
  public static <V> List<V> getList(String key) {
    LOG.debug(" getList key :{}", key);
    try {
      if (isClose() || isEmpty(key))
        return null; 
      key = buildKey(key);
      return redisTemplate.opsForList().range(key, 0L, -1L);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return null;
    } 
  }
  
  public static void trimList(String key, int start, int end) {
    LOG.debug(" trimList key :{}", key);
    try {
      if (isClose() || isEmpty(key))
        return; 
      key = buildKey(key);
      redisTemplate.opsForList().trim(key, start, end);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    } 
  }
  
  public static Object getIndexList(String key, int index) {
    LOG.debug(" getIndexList key :{}, index:{}", key, Integer.valueOf(index));
    try {
      if (isClose() || isEmpty(key) || index < 0)
        return null; 
      key = buildKey(key);
      return redisTemplate.opsForList().index(key, index);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return null;
    } 
  }
  
  public static boolean addList(String key, Object value) {
    LOG.debug(" addList key :{}, value:{}", key, value);
    try {
      if (isClose() || isEmpty(key) || isEmpty(value))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForList().leftPush(key, value);
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static boolean delList(String key, Object value) {
    LOG.debug(" delList key :{}, value:{}", key, value);
    try {
      if (isClose() || isEmpty(key) || isEmpty(value))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForList().remove(key, 0L, value);
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static <T> boolean setList(String key, List<T> list, long seconds) {
    LOG.debug(" setList key :{}, value:{}, seconds:{}", key, list);
    try {
      if (isClose() || isEmpty(key) || isEmpty(list))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForList().leftPushAll(key, list.toArray());
      if (seconds > 0L)
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS); 
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static <T> boolean setSet(String key, Set<T> set) {
    LOG.debug(" setSet key :{}, value:{}", key, set);
    try {
      if (isClose() || isEmpty(key) || isEmpty(set))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForSet().add(key, set.toArray());
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static boolean addSet(String key, Object value) {
    LOG.debug(" addSet key :{}, value:{}", key, value);
    try {
      if (isClose() || isEmpty(key) || isEmpty(value))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForSet().add(key, new Object[] { value });
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static <T> boolean setSet(String key, Set<T> set, long seconds) {
    LOG.debug(" setSet key :{}, value:{}, seconds:{}", key, set);
    try {
      if (isClose() || isEmpty(key) || isEmpty(set))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForSet().add(key, set.toArray());
      if (seconds > 0L)
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS); 
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static <T> Set<T> getSet(String key) {
    LOG.debug(" getSet key :{}", key);
    try {
      if (isClose() || isEmpty(key))
        return null; 
      key = buildKey(key);
      return redisTemplate.opsForSet().members(key);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return null;
    } 
  }
  
  public static <T> List<T> sortPageList(String key, String subKey, String by, boolean isDesc, boolean isAlpha, int off, int num) throws Exception {
    SortQueryBuilder<String> builder = SortQueryBuilder.sort(key);
    builder.by(String.valueOf(subKey) + "*->" + by);
    builder.get("#");
    builder.alphabetical(isAlpha);
    if (isDesc)
      builder.order(SortParameters.Order.DESC); 
    builder.limit(off, num);
    List<String> cks = redisTemplate.sort(builder.build());
    List<T> result = new ArrayList<>();
    for (String str : cks);
    return result;
  }
  
  public static boolean addZSet(String key, Object value, double score) {
    LOG.debug(" addZSet key :{},value:{}, score:{}", key, value);
    try {
      if (isClose() || isEmpty(key) || isEmpty(value))
        return false; 
      key = buildKey(key);
      return redisTemplate.opsForZSet().add(key, value, score).booleanValue();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static boolean removeZSet(String key, Object value) {
    LOG.debug(" removeZSet key :{},value:{}", key, value);
    try {
      if (isClose() || isEmpty(key) || isEmpty(value))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForZSet().remove(key, new Object[] { value });
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static boolean removeZSet(String key, long start, long end) {
    LOG.debug(" removeZSet key :{},start:{}, end:{}", key, Long.valueOf(start));
    try {
      if (isClose() || isEmpty(key))
        return false; 
      key = buildKey(key);
      redisTemplate.opsForZSet().removeRange(key, start, end);
      return true;
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return false;
    } 
  }
  
  public static <T> Set<T> getZSet(String key, long start, long end) {
    LOG.debug(" getZSet key :{},start:{}, end:{}", key, Long.valueOf(start));
    try {
      if (isClose() || isEmpty(key))
        return Collections.emptySet(); 
      key = buildKey(key);
      return redisTemplate.opsForZSet().range(key, start, end);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return Collections.emptySet();
    } 
  }
  
  public static long getZSetSize(String key) {
    try {
      key = buildKey(key);
      return redisTemplate.opsForZSet().size(key).longValue();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return 0L;
    } 
  }
  
  public static Double getZSetScore(Object key, Object value) {
    try {
      key = buildKey((String)key);
      return redisTemplate.opsForZSet().score(key, value);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return Double.valueOf(0.0D);
    } 
  }
  
  public static <T> Set<T> getZSetByScore(String key, double start, double end, long offset, long count) {
    try {
      if (isClose() || isEmpty(key))
        return Collections.emptySet(); 
      key = buildKey(key);
      return redisTemplate.opsForZSet().reverseRangeByScore(key, start, end, offset, count);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return Collections.emptySet();
    } 
  }
  
  public static <T> Set<T> getZSetByScore(String key, double start, double end) {
    try {
      if (isClose() || isEmpty(key))
        return Collections.emptySet(); 
      key = buildKey(key);
      return redisTemplate.opsForZSet().reverseRangeByScore(key, start, end);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return Collections.emptySet();
    } 
  }
  
  public static long getZSetLengthByScore(String key, double start, double end) {
    try {
      if (isClose() || isEmpty(key))
        return 0L; 
      key = buildKey(key);
      return redisTemplate.opsForZSet().count(key, start, end).longValue();
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return 0L;
    } 
  }
  
  public static boolean isPKexist(String key, String pk) {
    SetOperations setOperations = redisTemplate.opsForSet();
    return setOperations.isMember(key, pk).booleanValue();
  }
  
  public static String queryVisitArticleStatistics(int cgfId) {
    try {
      JedisConnectionFactory connectionFactory = (JedisConnectionFactory)redisTemplate.getConnectionFactory();
      connectionFactory.setDatabase(cgfId);
      return "SUCCESS";
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return "FAILURE";
    } 
  }
}
