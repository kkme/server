package com.hifun.soul.common;

/**
 *  用完之后需要销毁的对象接口
 *
 */
public interface IDestroyRequired {
	/**
	 * 销毁对象内容
	 */
	public void destroy();
	
}
