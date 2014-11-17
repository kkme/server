package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 关卡剧情信息
 *
 * @author SevenSoul
 */
@Component
public class GCStageDramaInfo extends GCMessage{
	
	/** 关卡剧情信息 */
	private com.hifun.soul.gameserver.stage.model.StageDramaInfo[] stageDramaInfos;
	/** 关卡id */
	private int stageId;
	/** 是否战斗前的剧情 */
	private boolean beforeBattle;

	public GCStageDramaInfo (){
	}
	
	public GCStageDramaInfo (
			com.hifun.soul.gameserver.stage.model.StageDramaInfo[] stageDramaInfos,
			int stageId,
			boolean beforeBattle ){
			this.stageDramaInfos = stageDramaInfos;
			this.stageId = stageId;
			this.beforeBattle = beforeBattle;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		stageDramaInfos = new com.hifun.soul.gameserver.stage.model.StageDramaInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.stage.model.StageDramaInfo objstageDramaInfos = new com.hifun.soul.gameserver.stage.model.StageDramaInfo();
			stageDramaInfos[i] = objstageDramaInfos;
					objstageDramaInfos.setStageId(readInteger());
							objstageDramaInfos.setOrder(readInteger());
							objstageDramaInfos.setDramaType(readInteger());
							objstageDramaInfos.setContent(readString());
							objstageDramaInfos.setName(readString());
							objstageDramaInfos.setIcon(readInteger());
							objstageDramaInfos.setPositionType(readInteger());
				}
		stageId = readInteger();
		beforeBattle = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(stageDramaInfos.length);
	for(int i=0; i<stageDramaInfos.length; i++){
	com.hifun.soul.gameserver.stage.model.StageDramaInfo objstageDramaInfos = stageDramaInfos[i];
				writeInteger(objstageDramaInfos.getStageId());
				writeInteger(objstageDramaInfos.getOrder());
				writeInteger(objstageDramaInfos.getDramaType());
				writeString(objstageDramaInfos.getContent());
				writeString(objstageDramaInfos.getName());
				writeInteger(objstageDramaInfos.getIcon());
				writeInteger(objstageDramaInfos.getPositionType());
	}
		writeInteger(stageId);
		writeBoolean(beforeBattle);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STAGE_DRAMA_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STAGE_DRAMA_INFO";
	}

	public com.hifun.soul.gameserver.stage.model.StageDramaInfo[] getStageDramaInfos(){
		return stageDramaInfos;
	}

	public void setStageDramaInfos(com.hifun.soul.gameserver.stage.model.StageDramaInfo[] stageDramaInfos){
		this.stageDramaInfos = stageDramaInfos;
	}	

	public int getStageId(){
		return stageId;
	}
		
	public void setStageId(int stageId){
		this.stageId = stageId;
	}

	public boolean getBeforeBattle(){
		return beforeBattle;
	}
		
	public void setBeforeBattle(boolean beforeBattle){
		this.beforeBattle = beforeBattle;
	}
}