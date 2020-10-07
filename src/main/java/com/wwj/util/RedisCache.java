package com.wwj.util;

import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import redis.clients.jedis.Jedis;

/**
 * Redis缓存策略实现
 */
public class RedisCache implements Cache {

    // 初始化Jedis
    private Jedis jedis = new Jedis("192.168.190.134", 6379);

    /*
     *  MyBatis会把映射文件的命名空间作为
     *  唯一标识cacheId，标识这个缓存策略属于哪个namespace
     *  这里定义好，并提供一个构造器，初始化这个cacheId即可
     */

    private String cacheId;

    public RedisCache(String cacheId) {
        this.cacheId = cacheId;
    }

    /**
     * 清空缓存
     */
    @Override
    public void clear() {
        // 但这方法不建议实现
    }

    @Override
    public String getId() {
        return cacheId;
    }

    /**
     * MyBatis会自动调用这个方法检测缓存
     * 中是否存在该对象。既然是自己实现的缓存
     * ，那么当然是到Redis中找了。
     */
    @Override
    public Object getObject(Object arg0) {
        // arg0 在这里是键
        try {

            byte[] bt = jedis.get(SerializableTools.objectToByteArray(arg0));
            if (bt == null) {        // 如果没有这个对象，直接返回null
                return null;
            }
            return SerializableTools.byteArrayToObject(bt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return new ReentrantReadWriteLock();
    }

    @Override
    public int getSize() {
        return Integer.parseInt(Long.toString(jedis.dbSize()));
    }

    /**
     * MyBatis在读取数据时，会自动调用此方法
     * 将数据设置到缓存中。这里就写入Redis
     */
    @Override
    public void putObject(Object arg0, Object arg1) {
        /*
         *  arg0是key , arg1是值
         *  MyBatis会把查询条件当做键，查询结果当做值。
         */
        try {
            jedis.set(SerializableTools.objectToByteArray(arg0), SerializableTools.objectToByteArray(arg1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * MyBatis缓存策略会自动检测内存的大小，由此
     * 决定是否删除缓存中的某些数据
     */
    @Override
    public Object removeObject(Object arg0) {
        Object object = getObject(arg0);
        try {
            jedis.del(SerializableTools.objectToByteArray(arg0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }
}