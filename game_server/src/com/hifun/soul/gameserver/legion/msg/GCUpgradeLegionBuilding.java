package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应升级军团建筑 
 *
 * @author SevenSoul
 */
@Component
public class GCUpgradeLegionBuilding extends GCMessage{
	
	/** 建筑类型 */
	private int buildingType;

	public GCUpgradeLegionBuilding (){
	}
	
	public GCUpgradeLegionBuilding (
			int buildingType ){
			this.buildingType = buildingType;
	}

	@Override
	protected boolean readImpl() {
		buildingType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(buildingType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPGRADE_LEGION_BUILDING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPGRADE_LEGION_BUILDING";
	}

	public int getBuildingType(){
		return buildingType;
	}
		
	public void setBuildingType(int buildingType){
		this.buildingType = buildingType;
	}
}