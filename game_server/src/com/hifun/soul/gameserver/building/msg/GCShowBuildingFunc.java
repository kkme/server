package com.hifun.soul.gameserver.building.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回建筑功能列表
 *
 * @author SevenSoul
 */
@Component
public class GCShowBuildingFunc extends GCMessage{
	
	/** 建筑类型(建筑id) */
	private int buildingType;
	/** 建筑功能列表 */
	private com.hifun.soul.gameserver.building.msg.BuildingFuncInfo[] funcs;

	public GCShowBuildingFunc (){
	}
	
	public GCShowBuildingFunc (
			int buildingType,
			com.hifun.soul.gameserver.building.msg.BuildingFuncInfo[] funcs ){
			this.buildingType = buildingType;
			this.funcs = funcs;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		buildingType = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		funcs = new com.hifun.soul.gameserver.building.msg.BuildingFuncInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.building.msg.BuildingFuncInfo objfuncs = new com.hifun.soul.gameserver.building.msg.BuildingFuncInfo();
			funcs[i] = objfuncs;
					objfuncs.setFuncId(readInteger());
							objfuncs.setName(readString());
							objfuncs.setDesc(readString());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(buildingType);
	writeShort(funcs.length);
	for(int i=0; i<funcs.length; i++){
	com.hifun.soul.gameserver.building.msg.BuildingFuncInfo objfuncs = funcs[i];
				writeInteger(objfuncs.getFuncId());
				writeString(objfuncs.getName());
				writeString(objfuncs.getDesc());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_BUILDING_FUNC;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_BUILDING_FUNC";
	}

	public int getBuildingType(){
		return buildingType;
	}
		
	public void setBuildingType(int buildingType){
		this.buildingType = buildingType;
	}

	public com.hifun.soul.gameserver.building.msg.BuildingFuncInfo[] getFuncs(){
		return funcs;
	}

	public void setFuncs(com.hifun.soul.gameserver.building.msg.BuildingFuncInfo[] funcs){
		this.funcs = funcs;
	}	
}