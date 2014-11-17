package com.hifun.soul.gamedb.util;

import java.io.IOException;

import org.apache.mina.common.ByteBuffer;

import com.google.protobuf.Message.Builder;

/**
 * protobuf相关的工具;
 * 
 * @author crazyjohn
 * 
 */
public class ProtobufUtil {
	/** 默认分配的buffer大小 */
	private static final int DEFAULT_BUFFER_SIZE = 64;

	/**
	 * 把指定的protobuf的builder序列化成字节流,并且写入结束标示符;
	 * 
	 * @param builder
	 *            需要写入的builder
	 * @return 返回序列化成的字节数组
	 * @throws IOException
	 *             当写入遇到IO异常的时候抛出
	 */
	public static byte[] writeBuilderWithDelimited(Builder builder)
			throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(DEFAULT_BUFFER_SIZE)
				.setAutoExpand(true);
		builder.build().writeDelimitedTo(buffer.asOutputStream());
		return buffer.array();
	}

	public static Builder readBytesWithDelimitedToBuilder(Builder builder,
			ByteBuffer buffer) throws IOException {
		builder.mergeDelimitedFrom(buffer.asInputStream());
		return builder;
	}
}
