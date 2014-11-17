package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示押运标签页
 *
 * @author SevenSoul
 */
@Component
public class GCShowEscortTab extends GCMessage{
	
	/** 押运信息 */
	private com.hifun.soul.gameserver.escort.info.EscortInfo escortInfo;

	public GCShowEscortTab (){
	}
	
	public GCShowEscortTab (
			com.hifun.soul.gameserver.escort.info.EscortInfo escortInfo ){
			this.escortInfo = escortInfo;
	}

	@Override
	protected boolean readImpl() {
		escortInfo = new com.hifun.soul.gameserver.escort.info.EscortInfo();
						escortInfo.setEscortId(readLong());
						escortInfo.setMonsterModelId(readInteger());
						escortInfo.setMonsterType(readInteger());
						escortInfo.setOwnerId(readLong());
						escortInfo.setOwnerName(readString());
						escortInfo.setOwnerLevel(readInteger());
						escortInfo.setOwnerLegionName(readString());
						escortInfo.setHelperId(readLong());
						escortInfo.setHelperName(readString());
						escortInfo.setHelperLevel(readInteger());
						escortInfo.setRemainBeRobbedNum(readInteger());
						escortInfo.setMaxBeRobbedNum(readInteger());
						escortInfo.setEscortRemainTime(readInteger());
						escortInfo.setEncourageRate(readInteger());
						escortInfo.setEscortRewardCoin(readInteger());
						escortInfo.setHelpRewardCoin(readInteger());
						escortInfo.setRobRewardCoin(readInteger());
						escortInfo.setEscortTime(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(escortInfo.getEscortId());
		writeInteger(escortInfo.getMonsterModelId());
		writeInteger(escortInfo.getMonsterType());
		writeLong(escortInfo.getOwnerId());
		writeString(escortInfo.getOwnerName());
		writeInteger(escortInfo.getOwnerLevel());
		writeString(escortInfo.getOwnerLegionName());
		writeLong(escortInfo.getHelperId());
		writeString(escortInfo.getHelperName());
		writeInteger(escortInfo.getHelperLevel());
		writeInteger(escortInfo.getRemainBeRobbedNum());
		writeInteger(escortInfo.getMaxBeRobbedNum());
		writeInteger(escortInfo.getEscortRemainTime());
		writeInteger(escortInfo.getEncourageRate());
		writeInteger(escortInfo.getEscortRewardCoin());
		writeInteger(escortInfo.getHelpRewardCoin());
		writeInteger(escortInfo.getRobRewardCoin());
		writeInteger(escortInfo.getEscortTime());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_ESCORT_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_ESCORT_TAB";
	}

	public com.hifun.soul.gameserver.escort.info.EscortInfo getEscortInfo(){
		return escortInfo;
	}
		
	public void setEscortInfo(com.hifun.soul.gameserver.escort.info.EscortInfo escortInfo){
		this.escortInfo = escortInfo;
	}
}