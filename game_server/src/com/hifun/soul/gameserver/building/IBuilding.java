package com.hifun.soul.gameserver.building;

import com.hifun.soul.gameserver.building.template.BuildingTemplate;
import com.hifun.soul.gameserver.human.Human;

/**
 * 建筑接口
 * 
 * @author magicstone
 *
 */
public interface IBuilding {

	/**
	 * 建筑类型
	 * 
	 * @return
	 */
	public Integer getBuildingType();
	
	
	/**
	 * 获取建筑模版
	 * 
	 * @return
	 */
	public BuildingTemplate getTemplate();
	
	
	/**
	 * human点击UUID对应角色的funcId对应的功能
	 * 
	 * @param human
	 * @param UUID
	 * @param funcId
	 */
	public void onClickFunc(Human human, long UUID, int funcId);
}
