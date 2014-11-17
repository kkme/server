package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回关卡信息
 *
 * @author SevenSoul
 */
@Component
public class GCStageInfo extends GCMessage{
	
	/** 关卡信息 */
	private com.hifun.soul.gameserver.stage.model.StageInfo stageInfo;

	public GCStageInfo (){
	}
	
	public GCStageInfo (
			com.hifun.soul.gameserver.stage.model.StageInfo stageInfo ){
			this.stageInfo = stageInfo;
	}

	@Override
	protected boolean readImpl() {
		stageInfo = new com.hifun.soul.gameserver.stage.model.StageInfo();
						stageInfo.setStageId(readInteger());
						stageInfo.setX(readInteger());
						stageInfo.setY(readInteger());
						stageInfo.setStageName(readString());
						stageInfo.setStageDesc(readString());
						stageInfo.setMinLevel(readInteger());
						stageInfo.setMonsterId(readInteger());
						stageInfo.setMonsterName(readString());
						stageInfo.setMonsterLevel(readInteger());
						stageInfo.setRewardExperience(readInteger());
						stageInfo.setRewardCurrencyType(readShort());
						stageInfo.setRewardCurrencyNum(readInteger());
						stageInfo.setItemName(readString());
						stageInfo.setMonsterOccupation(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(stageInfo.getStageId());
		writeInteger(stageInfo.getX());
		writeInteger(stageInfo.getY());
		writeString(stageInfo.getStageName());
		writeString(stageInfo.getStageDesc());
		writeInteger(stageInfo.getMinLevel());
		writeInteger(stageInfo.getMonsterId());
		writeString(stageInfo.getMonsterName());
		writeInteger(stageInfo.getMonsterLevel());
		writeInteger(stageInfo.getRewardExperience());
		writeShort(stageInfo.getRewardCurrencyType());
		writeInteger(stageInfo.getRewardCurrencyNum());
		writeString(stageInfo.getItemName());
		writeInteger(stageInfo.getMonsterOccupation());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STAGE_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STAGE_INFO";
	}

	public com.hifun.soul.gameserver.stage.model.StageInfo getStageInfo(){
		return stageInfo;
	}
		
	public void setStageInfo(com.hifun.soul.gameserver.stage.model.StageInfo stageInfo){
		this.stageInfo = stageInfo;
	}
}