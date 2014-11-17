package com.hifun.soul.gameserver.building.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.model.building.BuildingInfo;
import com.hifun.soul.gameserver.building.AbstractBuilding;
import com.hifun.soul.gameserver.building.BuildingType;
import com.hifun.soul.gameserver.building.assist.BuildingConverter;
import com.hifun.soul.gameserver.building.assist.BuildingFactory;
import com.hifun.soul.gameserver.human.Human;

/**
 * 建筑管理器
 * 
 * @author magicstone
 *
 */
public class HumanBuildingManager{
	private Human human;
	/** 建筑 Id 字典 */
	private Map<Integer, AbstractBuilding> buildingIdMap = new HashMap<Integer, AbstractBuilding>();
	
	public HumanBuildingManager(Human human) {
		this.human = human;
		init();
	}
	
	private void init() {
		// 初始化各个building信息
		for(BuildingType buildingType : BuildingType.values()){
			// 获取建筑对象
			AbstractBuilding building = BuildingFactory.createBuilding(buildingType.getIndex());
			if(building != null) {
				buildingIdMap.put(buildingType.getIndex(), building);
			}
		}
	}
	
	public boolean canSee(int funcId) {
		return true;
	}
	
	public boolean containsFunc(int buildingType, int funcId) {
		return true;
	}
	
	public Human getHuman() {
		return human;
	}
	
	public void onClickBuilding(long UUID, int buildingType) {
		// 获取建筑对象
		AbstractBuilding building = getBuildingByBuildingType(buildingType);

		if (building != null) {
			building.onClick(human,UUID);
		}
	}
	
	public void onClickBuildingFunc(long UUID, int buildingType, int funcId) {
		if(!containsFunc(buildingType,funcId)){
			return;
		}
		
		AbstractBuilding building = getBuildingByBuildingType(buildingType);
		
		if(building != null){
			building.onClickFunc(human, UUID, funcId);
		}
	}
	
	public AbstractBuilding getBuildingByBuildingType(Integer buildingType) {
		return buildingIdMap.get(buildingType);
	}

	/**
	 * 获取建筑信息
	 * 
	 * @return
	 */
	public BuildingInfo[] getBuildingInfos() {
		if(buildingIdMap == null
				|| buildingIdMap.values().size() == 0){
			return new BuildingInfo[0];
		}
		
		List<BuildingInfo> buildings = new ArrayList<BuildingInfo>();
		for(AbstractBuilding building : buildingIdMap.values()){
			BuildingInfo buildingInfo = BuildingConverter.convertAbstractBuildingToBuildingInfo(building);
			
			buildings.add(buildingInfo);
		}
		
		return buildings.toArray(new BuildingInfo[buildings.size()]);
	}
}
