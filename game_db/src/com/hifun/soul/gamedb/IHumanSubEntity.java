package com.hifun.soul.gamedb;


/**
 * 角色子实体接口;<br>
 * 实现此接口的实体都可以取出对应的角色ID;
 * 
 * @author crazyjohn
 * 
 */
public interface IHumanSubEntity{
	/**
	 * 获取角色的guid;
	 * 
	 * @return
	 */
	public long getHumanGuid();
}
