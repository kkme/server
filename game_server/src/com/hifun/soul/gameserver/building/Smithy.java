package com.hifun.soul.gameserver.building;

/**
 * 铁匠铺
 * 
 * @author magicstone
 *
 */
public class Smithy extends AbstractBuilding {

	@Override
	public Integer getBuildingType() {
		return BuildingType.SMITHY.getIndex();
	}

	@Override
	public void heartBeat() {

	}

}
