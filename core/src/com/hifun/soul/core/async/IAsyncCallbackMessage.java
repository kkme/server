package com.hifun.soul.core.async;

/**
 * 异步回调消息接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IAsyncCallbackMessage {

	/**
	 * 获取上下文ID;
	 * 
	 * @return 返回上下文ID;
	 */
	public long getContextId();

	/**
	 * 设置上下文ID;
	 * 
	 * @param id
	 */
	public void setContextId(long id);
}
