package com.cacheproxy.rediscloud.pool;

import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import com.cacheproxy.rediscloud.client.conn.IRedisConnection;
import com.cacheproxy.rediscloud.client.conn.RedisConnection;
import com.cacheproxy.rediscloud.config.RedisConnectionPoolConfig;

/**
 * @desc
 * @author liya
 * @emial lijiaqiya@163.com
 * @date 2017-3-7
 */
public class CollectionFactory implements PooledObjectFactory<IRedisConnection> {

	private RedisConnectionPoolConfig poolConfig;

	public CollectionFactory(RedisConnectionPoolConfig poolConfig) {
		super();
		this.poolConfig = poolConfig;
	}

	@Override
	public PooledObject<IRedisConnection> makeObject() throws Exception {
		return new DefaultPooledObject<IRedisConnection>(new RedisConnection(poolConfig));
	}

	@Override
	public void destroyObject(PooledObject<IRedisConnection> p)
			throws Exception {
		// 释放连接
		IRedisConnection connection = p.getObject();
		connection.close();

	}

	@Override
	public boolean validateObject(PooledObject<IRedisConnection> p) {
		// 验证 redis 连接
		IRedisConnection connection = p.getObject();
		return connection.isAvailable();
	}

	@Override
	public void activateObject(PooledObject<IRedisConnection> p)
			throws Exception {
		// 可以加入自己的一些逻辑 nothing to do

	}

	@Override
	public void passivateObject(PooledObject<IRedisConnection> p)
			throws Exception {
		// 可以加入自己的一些逻辑 nothing to do
	}

}
