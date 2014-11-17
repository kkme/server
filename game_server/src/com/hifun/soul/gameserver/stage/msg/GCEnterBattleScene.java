package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回征战场景信息
 *
 * @author SevenSoul
 */
@Component
public class GCEnterBattleScene extends GCMessage{
	
	/** 关卡地图 */
	private com.hifun.soul.gameserver.stage.model.StageMapInfo stageMapInfo;
	/** 地图据点信息集合 */
	private com.hifun.soul.gameserver.stage.model.MapStrongHoldInfo[] mapStrongHoldInfos;

	public GCEnterBattleScene (){
	}
	
	public GCEnterBattleScene (
			com.hifun.soul.gameserver.stage.model.StageMapInfo stageMapInfo,
			com.hifun.soul.gameserver.stage.model.MapStrongHoldInfo[] mapStrongHoldInfos ){
			this.stageMapInfo = stageMapInfo;
			this.mapStrongHoldInfos = mapStrongHoldInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		stageMapInfo = new com.hifun.soul.gameserver.stage.model.StageMapInfo();
						stageMapInfo.setMapId(readInteger());
						stageMapInfo.setMapName(readString());
						stageMapInfo.setMapDesc(readString());
						stageMapInfo.setIcon(readInteger());
						stageMapInfo.setPassCount(readInteger());
						stageMapInfo.setTotalCount(readInteger());
						stageMapInfo.setSimpleIcon(readInteger());
						stageMapInfo.setChapter(readString());
						stageMapInfo.setMapState(readInteger());
				count = readShort();
		count = count < 0 ? 0 : count;
		mapStrongHoldInfos = new com.hifun.soul.gameserver.stage.model.MapStrongHoldInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.stage.model.MapStrongHoldInfo objmapStrongHoldInfos = new com.hifun.soul.gameserver.stage.model.MapStrongHoldInfo();
			mapStrongHoldInfos[i] = objmapStrongHoldInfos;
					objmapStrongHoldInfos.setStrongholdId(readInteger());
							objmapStrongHoldInfos.setBgId(readInteger());
							objmapStrongHoldInfos.setResourceId(readInteger());
							objmapStrongHoldInfos.setX(readInteger());
							objmapStrongHoldInfos.setY(readInteger());
							objmapStrongHoldInfos.setName(readString());
							objmapStrongHoldInfos.setDesc(readString());
							objmapStrongHoldInfos.setIsOpen(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(stageMapInfo.getMapId());
		writeString(stageMapInfo.getMapName());
		writeString(stageMapInfo.getMapDesc());
		writeInteger(stageMapInfo.getIcon());
		writeInteger(stageMapInfo.getPassCount());
		writeInteger(stageMapInfo.getTotalCount());
		writeInteger(stageMapInfo.getSimpleIcon());
		writeString(stageMapInfo.getChapter());
		writeInteger(stageMapInfo.getMapState());
	writeShort(mapStrongHoldInfos.length);
	for(int i=0; i<mapStrongHoldInfos.length; i++){
	com.hifun.soul.gameserver.stage.model.MapStrongHoldInfo objmapStrongHoldInfos = mapStrongHoldInfos[i];
				writeInteger(objmapStrongHoldInfos.getStrongholdId());
				writeInteger(objmapStrongHoldInfos.getBgId());
				writeInteger(objmapStrongHoldInfos.getResourceId());
				writeInteger(objmapStrongHoldInfos.getX());
				writeInteger(objmapStrongHoldInfos.getY());
				writeString(objmapStrongHoldInfos.getName());
				writeString(objmapStrongHoldInfos.getDesc());
				writeBoolean(objmapStrongHoldInfos.getIsOpen());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ENTER_BATTLE_SCENE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ENTER_BATTLE_SCENE";
	}

	public com.hifun.soul.gameserver.stage.model.StageMapInfo getStageMapInfo(){
		return stageMapInfo;
	}
		
	public void setStageMapInfo(com.hifun.soul.gameserver.stage.model.StageMapInfo stageMapInfo){
		this.stageMapInfo = stageMapInfo;
	}

	public com.hifun.soul.gameserver.stage.model.MapStrongHoldInfo[] getMapStrongHoldInfos(){
		return mapStrongHoldInfos;
	}

	public void setMapStrongHoldInfos(com.hifun.soul.gameserver.stage.model.MapStrongHoldInfo[] mapStrongHoldInfos){
		this.mapStrongHoldInfos = mapStrongHoldInfos;
	}	
}