package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 下发角色军团头衔信息
 *
 * @author SevenSoul
 */
@Component
public class GCSendHumanLegionTitleInfo extends GCMessage{
	
	/** 角色军团头衔信息 */
	private com.hifun.soul.gameserver.legion.info.HumanLegionTitleInfo humanLegionTitleInfo;

	public GCSendHumanLegionTitleInfo (){
	}
	
	public GCSendHumanLegionTitleInfo (
			com.hifun.soul.gameserver.legion.info.HumanLegionTitleInfo humanLegionTitleInfo ){
			this.humanLegionTitleInfo = humanLegionTitleInfo;
	}

	@Override
	protected boolean readImpl() {
		humanLegionTitleInfo = new com.hifun.soul.gameserver.legion.info.HumanLegionTitleInfo();
						humanLegionTitleInfo.setTitleId(readInteger());
						humanLegionTitleInfo.setTitelName(readString());
						humanLegionTitleInfo.setIsAllProperty(readBoolean());
						humanLegionTitleInfo.setPropertyId(readInteger());
						humanLegionTitleInfo.setPropertyRate(readInteger());
						humanLegionTitleInfo.setEndTime(readString());
						humanLegionTitleInfo.setValid(readBoolean());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(humanLegionTitleInfo.getTitleId());
		writeString(humanLegionTitleInfo.getTitelName());
		writeBoolean(humanLegionTitleInfo.getIsAllProperty());
		writeInteger(humanLegionTitleInfo.getPropertyId());
		writeInteger(humanLegionTitleInfo.getPropertyRate());
		writeString(humanLegionTitleInfo.getEndTime());
		writeBoolean(humanLegionTitleInfo.getValid());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SEND_HUMAN_LEGION_TITLE_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SEND_HUMAN_LEGION_TITLE_INFO";
	}

	public com.hifun.soul.gameserver.legion.info.HumanLegionTitleInfo getHumanLegionTitleInfo(){
		return humanLegionTitleInfo;
	}
		
	public void setHumanLegionTitleInfo(com.hifun.soul.gameserver.legion.info.HumanLegionTitleInfo humanLegionTitleInfo){
		this.humanLegionTitleInfo = humanLegionTitleInfo;
	}
}