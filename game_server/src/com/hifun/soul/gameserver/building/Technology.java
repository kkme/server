package com.hifun.soul.gameserver.building;

/**
 * 科技;
 * 
 * @author crazyjohn
 * 
 */
public class Technology extends AbstractBuilding {

	@Override
	public Integer getBuildingType() {
		return BuildingType.TECHNOLOGY.getIndex();
	}

	@Override
	public void heartBeat() {
		// TODO Auto-generated method stub

	}

}
