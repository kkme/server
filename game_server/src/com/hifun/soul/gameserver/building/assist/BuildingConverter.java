package com.hifun.soul.gameserver.building.assist;

import com.hifun.soul.common.model.building.BuildingInfo;
import com.hifun.soul.gameserver.building.AbstractBuilding;

/**
 * 通用的转换操作
 * 
 * @author magicstone
 *
 */
public class BuildingConverter {

	public static BuildingInfo convertAbstractBuildingToBuildingInfo(AbstractBuilding building) {
		if(building == null){
			return null;
		}
		
		BuildingInfo buildingInfo = new BuildingInfo();
		buildingInfo.setBuildingId(building.getBuildingType());
		buildingInfo.setName(building.getName());
		buildingInfo.setDesc(building.getTemplate().getDesc());
		buildingInfo.setIcon(building.getIcon());
		buildingInfo.setLevel(building.getLevel());
		buildingInfo.setUpgrade(true);
		
		return buildingInfo;
	}
}
