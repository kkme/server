package com.hifun.soul.common;

/**
 * 可以心跳的接口;
 * 
 * @author crazyjohn
 * 
 */
public interface IHeartBeatable {
	/**
	 * 心跳動作;<br>
	 * heartBeat用来处理心跳的先关逻辑;
	 */
	public void heartBeat();
}
