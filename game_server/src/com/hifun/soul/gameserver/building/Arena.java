package com.hifun.soul.gameserver.building;

/**
 * 竞技场
 * 
 * @author magicstone
 *
 */
public class Arena extends AbstractBuilding {

	@Override
	public Integer getBuildingType() {
		return BuildingType.ARENA.getIndex();
	}

	@Override
	public void heartBeat() {

	}

}
