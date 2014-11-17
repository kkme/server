package com.hifun.soul.gameserver.building.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 升级建筑
 * 
 * @author SevenSoul
 */
@Component
public class CGUpgradeBuilding extends CGMessage{
	
	/** 建筑类型(建筑id) */
	private int buildingType;
	
	public CGUpgradeBuilding (){
	}
	
	public CGUpgradeBuilding (
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
		return MessageType.CG_UPGRADE_BUILDING;
	}
	
	@Override
	public String getTypeName() {
		return "CG_UPGRADE_BUILDING";
	}

	public int getBuildingType(){
		return buildingType;
	}
		
	public void setBuildingType(int buildingType){
		this.buildingType = buildingType;
	}

	@Override
	public void execute() {
	}
}