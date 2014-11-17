package com.hifun.soul.gameserver.building;

/**
 * 伐木场
 * 
 * @author magicstone
 *
 */
public class WoodHouse extends AbstractBuilding {

	@Override
	public Integer getBuildingType() {
		return BuildingType.WOODHOUSE.getIndex();
	}

	@Override
	public void heartBeat() {

	}

}
