package com.hifun.soul.gameserver.building.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 点击建筑
 * 
 * @author SevenSoul
 */
@Component
public class CGClickBuilding extends CGMessage{
	
	/** 建筑类型(建筑id) */
	private int buildingType;
	/** 点击玩家的UUID */
	private long UUID;
	
	public CGClickBuilding (){
	}
	
	public CGClickBuilding (
			int buildingType,
			long UUID ){
			this.buildingType = buildingType;
			this.UUID = UUID;
	}
	
	@Override
	protected boolean readImpl() {
		buildingType = readInteger();
		UUID = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(buildingType);
		writeLong(UUID);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLICK_BUILDING;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLICK_BUILDING";
	}

	public int getBuildingType(){
		return buildingType;
	}
		
	public void setBuildingType(int buildingType){
		this.buildingType = buildingType;
	}

	public long getUUID(){
		return UUID;
	}
		
	public void setUUID(long UUID){
		this.UUID = UUID;
	}

	@Override
	public void execute() {
	}
}