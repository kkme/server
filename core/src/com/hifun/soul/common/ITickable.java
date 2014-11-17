package com.hifun.soul.common;

/**
 * 可以滴答的接口;
 * 
 * @author crazyjohn
 * 
 */
public interface ITickable {

	/**
	 * 滴答;<br>
	 * tick接口用来按帧数处理消息;
	 */
	public void tick();
}
