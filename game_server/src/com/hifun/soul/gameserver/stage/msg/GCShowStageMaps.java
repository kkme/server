package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 关卡地图选择
 *
 * @author SevenSoul
 */
@Component
public class GCShowStageMaps extends GCMessage{
	
	/** 当前关卡所在地图 */
	private int mapId;
	/** 关卡地图 */
	private com.hifun.soul.gameserver.stage.model.StageMapInfo[] stageMapInfos;

	public GCShowStageMaps (){
	}
	
	public GCShowStageMaps (
			int mapId,
			com.hifun.soul.gameserver.stage.model.StageMapInfo[] stageMapInfos ){
			this.mapId = mapId;
			this.stageMapInfos = stageMapInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		mapId = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		stageMapInfos = new com.hifun.soul.gameserver.stage.model.StageMapInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.stage.model.StageMapInfo objstageMapInfos = new com.hifun.soul.gameserver.stage.model.StageMapInfo();
			stageMapInfos[i] = objstageMapInfos;
					objstageMapInfos.setMapId(readInteger());
							objstageMapInfos.setMapName(readString());
							objstageMapInfos.setMapDesc(readString());
							objstageMapInfos.setIcon(readInteger());
							objstageMapInfos.setPassCount(readInteger());
							objstageMapInfos.setTotalCount(readInteger());
							objstageMapInfos.setSimpleIcon(readInteger());
							objstageMapInfos.setChapter(readString());
							objstageMapInfos.setMapState(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(mapId);
	writeShort(stageMapInfos.length);
	for(int i=0; i<stageMapInfos.length; i++){
	com.hifun.soul.gameserver.stage.model.StageMapInfo objstageMapInfos = stageMapInfos[i];
				writeInteger(objstageMapInfos.getMapId());
				writeString(objstageMapInfos.getMapName());
				writeString(objstageMapInfos.getMapDesc());
				writeInteger(objstageMapInfos.getIcon());
				writeInteger(objstageMapInfos.getPassCount());
				writeInteger(objstageMapInfos.getTotalCount());
				writeInteger(objstageMapInfos.getSimpleIcon());
				writeString(objstageMapInfos.getChapter());
				writeInteger(objstageMapInfos.getMapState());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_STAGE_MAPS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_STAGE_MAPS";
	}

	public int getMapId(){
		return mapId;
	}
		
	public void setMapId(int mapId){
		this.mapId = mapId;
	}

	public com.hifun.soul.gameserver.stage.model.StageMapInfo[] getStageMapInfos(){
		return stageMapInfos;
	}

	public void setStageMapInfos(com.hifun.soul.gameserver.stage.model.StageMapInfo[] stageMapInfos){
		this.stageMapInfos = stageMapInfos;
	}	
}