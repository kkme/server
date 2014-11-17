package com.hifun.soul.core.orm;

import java.io.IOException;

import org.apache.mina.common.ByteBuffer;

/**
 * 基本的实体;
 * 
 * <pre>
 * 1. 此类实体没有blob字段;
 * 2. 是{@code BaseProtobufEntity} 以外的另一个实体分支;
 * </pre>
 * 
 * @author crazyjohn
 * 
 */
public abstract class BaseCommonEntity implements IEntity {

	@Override
	public void read(ByteBuffer buffer) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(ByteBuffer buffer) throws IOException {
		throw new UnsupportedOperationException();
	}

}
