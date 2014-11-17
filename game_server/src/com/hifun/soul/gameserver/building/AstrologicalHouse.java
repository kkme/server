package com.hifun.soul.gameserver.building;

/**
 * 占星屋
 * 
 * @author magicstone
 *
 */
public class AstrologicalHouse extends AbstractBuilding {

	@Override
	public Integer getBuildingType() {
		return BuildingType.ASTROLOGICALHOUSE.getIndex();
	}

	@Override
	public void heartBeat() {

	}

}
