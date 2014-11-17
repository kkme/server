package com.hifun.soul.gameserver.building;

/**
 * 矿场
 * 
 * @author magicstone
 *
 */
public class MineHouse extends AbstractBuilding {

	@Override
	public Integer getBuildingType() {
		return BuildingType.MINEHOUSE.getIndex();
	}

	@Override
	public void heartBeat() {

	}

}
