package com.hifun.soul.core.orm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.mina.common.ByteBuffer;

import com.google.protobuf.Message.Builder;

/**
 * 基础的protobuf实体<br>
 * FIXME: crazyjohn 注释待完善!!!
 * 
 * <pre>
 * 1. 子类实体要进行序列化的时候可以直接调用{{@link #read(ByteBuffer)},{{@link #write(ByteBuffer)};
 * 2. write和read都是final方法,不允许子类覆盖;
 * 3. 要到容器类型的实体比如角色的物品可能是:<br>
 * <code> 
 * message Human {
 * 	repeated Item items = 1;
 * }
 * 
 * message item {
 * 	required int64 itemId = 1;
 *  ...
 * }
 * </code>这种情况下在写入实体的时候需要调用{{@link #toByteArray(ByteBuffer)} 这个方法会写入一些限制信息;
 * 以便在反序列化的是不不出错;
 * </pre>
 * 
 * @author crazyjohn
 * 
 */
public abstract class BaseProtobufEntity<B extends Builder> implements
		IEntity, IProtobufEntity {
	protected B builder;


	protected BaseProtobufEntity(B builder) {
		this.builder = builder;
	}

	@Override
	public B getBuilder() {
		return builder;
	}

	@Override
	public final void write(ByteBuffer buffer) throws IOException {
		buffer.put(this.toByteArray(buffer));
	}

	@Override
	public final void read(ByteBuffer buffer) throws IOException {
		builder.mergeDelimitedFrom(buffer.asInputStream());
	}

	protected byte[] toByteArray(ByteBuffer buffer) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		builder.clone().build().writeDelimitedTo(baos);
		return baos.toByteArray();
	}

}
