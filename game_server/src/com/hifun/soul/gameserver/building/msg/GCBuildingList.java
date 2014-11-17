package com.hifun.soul.gameserver.building.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 建筑列表
 *
 * @author SevenSoul
 */
@Component
public class GCBuildingList extends GCMessage{
	
	/** 建筑列表 */
	private com.hifun.soul.common.model.building.BuildingInfo[] buildingInfo;

	public GCBuildingList (){
	}
	
	public GCBuildingList (
			com.hifun.soul.common.model.building.BuildingInfo[] buildingInfo ){
			this.buildingInfo = buildingInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		buildingInfo = new com.hifun.soul.common.model.building.BuildingInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.common.model.building.BuildingInfo objbuildingInfo = new com.hifun.soul.common.model.building.BuildingInfo();
			buildingInfo[i] = objbuildingInfo;
					objbuildingInfo.setBuildingId(readInteger());
							objbuildingInfo.setName(readString());
							objbuildingInfo.setDesc(readString());
							objbuildingInfo.setIcon(readInteger());
							objbuildingInfo.setLevel(readInteger());
							objbuildingInfo.setUpgrade(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(buildingInfo.length);
	for(int i=0; i<buildingInfo.length; i++){
	com.hifun.soul.common.model.building.BuildingInfo objbuildingInfo = buildingInfo[i];
				writeInteger(objbuildingInfo.getBuildingId());
				writeString(objbuildingInfo.getName());
				writeString(objbuildingInfo.getDesc());
				writeInteger(objbuildingInfo.getIcon());
				writeInteger(objbuildingInfo.getLevel());
				writeBoolean(objbuildingInfo.getUpgrade());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BUILDING_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BUILDING_LIST";
	}

	public com.hifun.soul.common.model.building.BuildingInfo[] getBuildingInfo(){
		return buildingInfo;
	}

	public void setBuildingInfo(com.hifun.soul.common.model.building.BuildingInfo[] buildingInfo){
		this.buildingInfo = buildingInfo;
	}	
}