package com.hifun.soul.core.orm;

import java.io.IOException;
import java.io.Serializable;

import org.apache.mina.common.ByteBuffer;

/**
 * 实体接口;<br>
 * 
 * <pre>
 * 1. Entity来源于企业级应用架构的Entity模式;
 * 2. Entity需要有一个唯一的不变的标识;
 * 3. 在此接口中这个标识就是ID;
 * </pre>
 * 
 * @author crazyjohn
 * 
 * @param <ID>
 */
public interface IEntity {

	/**
	 * 获取实体ID
	 * 
	 * @return 返回实体的ID;
	 * 
	 */
	public Serializable getId();

	/**
	 * 设置实体的ID;
	 * 
	 * @param id
	 */
	public void setId(Serializable id);

	/**
	 * 从数据缓存中反序列化出实体;
	 * 
	 * @param buffer
	 *            数据缓存
	 * @throws IOException
	 *             读取时候的IO异常
	 */
	public void read(ByteBuffer buffer) throws IOException;

	/**
	 * 把实体序列化到数据缓存中;
	 * 
	 * @param buffer
	 *            数据缓存;
	 * @throws IOException
	 *             读取时候的IO异常
	 */
	public void write(ByteBuffer buffer) throws IOException;
}