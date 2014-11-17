package com.hifun.soul.core.orm;

import com.google.protobuf.Message.Builder;

/**
 * Protobuf实体接口
 * 
 * @author crazyjohn
 * 
 */
public interface IProtobufEntity {

	/**
	 * 获取实体对应的Builder
	 * 
	 * @return
	 */
	public Builder getBuilder();
}
