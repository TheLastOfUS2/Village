package com.lanou.cache;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by dllo on 2017/11/2.
 */
public class RedisCache implements Cache {

    private static JedisConnectionFactory jedisConnectionFactory;

    private final String id;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public RedisCache(String id) {
        //如果传入的id为空,抛出参数异常
        if (id == null) {
            throw new IllegalArgumentException("缓存需要一个id");
        }
        this.id = id;
    }

    //获取缓存对象的唯一标识
    @Override
    public String getId() {
        return this.id;
    }

    //将参数的key和value存到缓存对象中
    @Override
    public void putObject(Object key, Object value) {
        JedisConnection connection = null;

        try {
            connection = (JedisConnection) jedisConnectionFactory.getConnection();

            //redis的序列化工具
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();

            //将key和value序列化后保存
            connection.set(serializer.serialize(key), serializer.serialize(value));

        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    //从缓存中获取值
    @Override
    public Object getObject(Object key) {
        JedisConnection connection = null;

        Object result = null;

        try {
            connection = (JedisConnection) jedisConnectionFactory.getConnection();

            //redis的序列化工具
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();

            //根据key值获取缓存中的值
            byte[] valueBytes = connection.get(serializer.serialize(key));
            result = serializer.deserialize(valueBytes);
        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();

            }
        }
        return result;
    }

    //根据key移除value
    //这个方法可选,核心代码里没有调用这个方法
    @Override
    public Object removeObject(Object key) {
        JedisConnection connection = null;

        Object result = null;

        try {
            connection = (JedisConnection) jedisConnectionFactory.getConnection();

            //redis的序列化工具
            RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();

            //第一个参数是key,第二个参数是多少秒之后移除
            result = connection.expire(serializer.serialize(key),0);

        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();

            }
        }
        return result;
    }

    //清空缓存
    @Override
    public void clear() {
        JedisConnection connection = null;

        try {
            connection = (JedisConnection) jedisConnectionFactory.getConnection();

            //清空缓存
            connection.flushDb();
            //或
            connection.flushAll();

        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();

            }
        }
    }

    //获取所有键值对的数量(可选方法)
    @Override
    public int getSize() {
        int count = 0;
        JedisConnection connection = null;

        try {
            connection = (JedisConnection) jedisConnectionFactory.getConnection();
            count = Integer.valueOf(connection.dbSize().toString());

        } catch (JedisConnectionException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return count;
    }

    //读写锁(可选)
    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.lock;
    }

    public static void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
        RedisCache.jedisConnectionFactory = jedisConnectionFactory;
    }
}
