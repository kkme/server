package com.hifun.soul.gameserver.building.function;

import com.hifun.soul.gameserver.building.AbstractBuilding;
import com.hifun.soul.gameserver.human.Human;

/**
 * 建筑功能接口
 * 
 * @author magicstone
 *
 */
public interface IBuildingFunc {

	/**
	 * 获取建筑功能类型
	 * 
	 * @return
	 */
	public Integer getBuildingFuncType();
	
	/**
	 * 是否可见
	 * 
	 * @return
	 */
	public boolean isOpen();
	
	/**
	 * 建筑功能执行
	 * 
	 */
	public void execute(Human human, long UUID, AbstractBuilding abstractBuilding);
	
	public String getName();
	
	public String getDesc();
}
