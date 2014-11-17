package com.hifun.soul.gameserver.building.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 点击建筑功能
 * 
 * @author SevenSoul
 */
@Component
public class CGClickBuildingFunc extends CGMessage{
	
	/** 建筑类型(建筑id) */
	private int buildingType;
	/** 功能id */
	private int funcId;
	/** 点击玩家的UUID */
	private long UUID;
	
	public CGClickBuildingFunc (){
	}
	
	public CGClickBuildingFunc (
			int buildingType,
			int funcId,
			long UUID ){
			this.buildingType = buildingType;
			this.funcId = funcId;
			this.UUID = UUID;
	}
	
	@Override
	protected boolean readImpl() {
		buildingType = readInteger();
		funcId = readInteger();
		UUID = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(buildingType);
		writeInteger(funcId);
		writeLong(UUID);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CLICK_BUILDING_FUNC;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLICK_BUILDING_FUNC";
	}

	public int getBuildingType(){
		return buildingType;
	}
		
	public void setBuildingType(int buildingType){
		this.buildingType = buildingType;
	}

	public int getFuncId(){
		return funcId;
	}
		
	public void setFuncId(int funcId){
		this.funcId = funcId;
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