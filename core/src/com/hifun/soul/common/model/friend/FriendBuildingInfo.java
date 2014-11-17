package com.hifun.soul.common.model.friend;

import com.hifun.soul.common.model.building.BuildingInfo;
import com.hifun.soul.common.model.human.CharacterInfo;

/**
 * 
 * 查看好友需要的信息
 * 
 * @author magicstone
 *
 */
public class FriendBuildingInfo {
	
	private CharacterInfo characterInfo;
	
	private BuildingInfo[] buildings;

	
	public CharacterInfo getCharacterInfo() {
		return characterInfo;
	}

	public void setCharacterInfo(CharacterInfo characterInfo) {
		this.characterInfo = characterInfo;
	}

	public BuildingInfo[] getBuildings() {
		return buildings;
	}

	public void setBuildings(BuildingInfo[] buildings) {
		this.buildings = buildings;
	}
	
}
