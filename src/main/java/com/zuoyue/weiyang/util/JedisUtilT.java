package com.zuoyue.weiyang.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.SafeEncoder;

import java.util.List;

public class JedisUtilT {

    /* 缓存生存时间 */
    private int expire = 60000;

    /*  对key的操作方法 */
    private static JedisPool jedisPool = null;

    public JedisPool getPool() {
        return jedisPool;
    }

    /* 从jedispool中获取jedis对象 */
    public Jedis getJedis() {
        if (jedisPool == null) {
            throw new NullPointerException();
        }
        return jedisPool.getResource();
    }

    public static JedisPool getJedisPool() {
        return jedisPool;
    }

    public static void setJedisPool(JedisPool jedisPool) {
        JedisUtilT.jedisPool = jedisPool;
    }

    /* 在finally中回收jedis */
    public void returnJedis(Jedis jedis) {
        if (null != jedis && null != jedisPool) {
            jedisPool.returnResource(jedis);
        }
    }

    /* 销毁链接(放入catch) */
    public static void returnBrokenResource(Jedis jedis) {
        if (null != jedis && null != jedisPool) {
            jedisPool.returnResource(jedis);
        }
    }

    /* 设置过期时间 */
    public void expire(String key, int seconds) {
        if (seconds < 0) {
            return;
        }
        Jedis jedis = getJedis();
        jedis.expire(key, seconds);
        returnJedis(jedis);
    }

    /* 设置默认过期时间 */
    public void expire(String key) {
        expire(key, expire);
    }

    /* 清空所有key */
    public String flushAll() {
        Jedis jedis = getJedis();
        String stata = jedis.flushAll();
        returnJedis(jedis);
        return stata;
    }

    /* 更改key 返回值是状态码 */
    public String rename(String oldkey, String newkey) {
        return rename(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
    }

    /* 更改key，仅当新key不存在时才执行 */
    public long renamex(String oldkey, String newkey) {
        Jedis jedis = getJedis();
        long status = jedis.renamenx(oldkey, newkey);
        returnJedis(jedis);
        return status;
    }

    /* 更改key */
    public String rename(byte[] oldkey, byte[] newkey) {
        Jedis jedis = getJedis();
        String status = jedis.rename(oldkey, newkey);
        returnJedis(jedis);
        return status;
    }

    /* 设置key的过期时间，以秒为单位，返回值是影响的记录数 */
    public long expired(String key, int seconds) {
        Jedis jedis = getJedis();
        long count = jedis.expire(key, seconds);
        returnJedis(jedis);
        return count;
    }

    /* 设置key的过期时间，它是距历元（即格林威治标准时间 1970年1月1日的00:00:00，格里高利历）偏移量 */
    public long expireAt(String key, long timestamp) {
        Jedis jedis = getJedis();
        long count = jedis.expireAt(key, timestamp);
        returnJedis(jedis);
        return count;
    }

    /* 查询key的过期时间，以秒为单位的时间表示返回的是指定key的剩余的生存时间 */
    public long ttl(String key) {
        Jedis sjedis = getJedis();
        long len = sjedis.ttl(key);
        returnJedis(sjedis);
        return len;
    }

    /* 取消对key过期时间的设置，将带生存时间的转换成一个用不过期的key，当移除成功时返回1，key不存在或者移除不成功时返回0 */
    public long persist(String key) {
        Jedis jedis = getJedis();
        long count = jedis.persist(key);
        returnJedis(jedis);
        return count;
    }

    /* 删除keys对应的记录，可以是多个key，返回值是被删除的数量 */
    public long del(String... keys) {
        Jedis jedis = getJedis();
        long count = jedis.del(keys);
        returnJedis(jedis);
        return count;
    }

    /* 删除keys对应的记录，可以是多个key */
    public long del(byte[]... keys) {
        Jedis jedis = getJedis();
        long count = jedis.del(keys);
        returnJedis(jedis);
        return count;
    }

    /* 判断key是否存在 */
    public boolean exists(String key) {
        Jedis jedis = getJedis();
        boolean exists = jedis.exists(key);
        returnJedis(jedis);
        return exists;
    }

    /* 对List，set，SortSet进行排序，如果集合数据较大应避免使用这个方法，返回排序后的结果，默认升序sort key Desc为降序 */
    public List<String> sort(String key) {
        Jedis jedis = getJedis();
        List<String> list = jedis.sort(key);
        returnJedis(jedis);
        return list;
    }


}
