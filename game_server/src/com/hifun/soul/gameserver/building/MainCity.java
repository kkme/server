package com.hifun.soul.gameserver.building;


/**
 * 
 * 主城
 * 
 * @author magicstone
 *
 */
public class MainCity extends AbstractBuilding {

	@Override
	public Integer getBuildingType() {
		return BuildingType.MAINCITY.getIndex();
	}

	@Override
	public void heartBeat() {

	}

}
