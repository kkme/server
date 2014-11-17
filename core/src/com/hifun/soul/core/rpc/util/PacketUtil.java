package com.hifun.soul.core.rpc.util;

import org.apache.mina.common.ByteBuffer;

/**
 * 解析消息包的工具类
 * 
 * @author crazyjohn
 * 
 */
public class PacketUtil {
	/**
	 * 生成请求消息包
	 * 
	 * @param buffer
	 * @return
	 */
	public static ByteBuffer cutPacket(ByteBuffer buffer) {
		ByteBuffer packet = null;
		if (buffer.limit() > buffer.position()) {
			int position = buffer.position();
			short len = buffer.getShort();
			buffer.position(position);
			packet = ByteBuffer.allocate(len);
			byte[] bytes = new byte[len];
			buffer.get(bytes);
			packet.put(bytes);
			packet.flip();
		}
		return packet;
	}
}
