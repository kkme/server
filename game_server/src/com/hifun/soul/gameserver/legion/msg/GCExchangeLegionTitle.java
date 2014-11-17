package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应兑换军团头衔 
 *
 * @author SevenSoul
 */
@Component
public class GCExchangeLegionTitle extends GCMessage{
	
	/** 军团头衔信息 */
	private com.hifun.soul.gameserver.legion.info.LegionTitleInfo legionTitleInfo;
	/** 个人勋章 */
	private int selfMedal;

	public GCExchangeLegionTitle (){
	}
	
	public GCExchangeLegionTitle (
			com.hifun.soul.gameserver.legion.info.LegionTitleInfo legionTitleInfo,
			int selfMedal ){
			this.legionTitleInfo = legionTitleInfo;
			this.selfMedal = selfMedal;
	}

	@Override
	protected boolean readImpl() {
		legionTitleInfo = new com.hifun.soul.gameserver.legion.info.LegionTitleInfo();
						legionTitleInfo.setTitleId(readInteger());
						legionTitleInfo.setTitelName(readString());
						legionTitleInfo.setIconId(readInteger());
						legionTitleInfo.setColorId(readInteger());
						legionTitleInfo.setNeedPositionName(readString());
						legionTitleInfo.setMaxNum(readInteger());
						legionTitleInfo.setRemainNum(readInteger());
						legionTitleInfo.setNeedMedal(readInteger());
						legionTitleInfo.setIsAllProperty(readBoolean());
						legionTitleInfo.setPropertyId(readInteger());
						legionTitleInfo.setPropertyRate(readInteger());
						legionTitleInfo.setValidDays(readInteger());
						legionTitleInfo.setNeedBuildingLevel(readInteger());
				selfMedal = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(legionTitleInfo.getTitleId());
		writeString(legionTitleInfo.getTitelName());
		writeInteger(legionTitleInfo.getIconId());
		writeInteger(legionTitleInfo.getColorId());
		writeString(legionTitleInfo.getNeedPositionName());
		writeInteger(legionTitleInfo.getMaxNum());
		writeInteger(legionTitleInfo.getRemainNum());
		writeInteger(legionTitleInfo.getNeedMedal());
		writeBoolean(legionTitleInfo.getIsAllProperty());
		writeInteger(legionTitleInfo.getPropertyId());
		writeInteger(legionTitleInfo.getPropertyRate());
		writeInteger(legionTitleInfo.getValidDays());
		writeInteger(legionTitleInfo.getNeedBuildingLevel());
		writeInteger(selfMedal);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_EXCHANGE_LEGION_TITLE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_EXCHANGE_LEGION_TITLE";
	}

	public com.hifun.soul.gameserver.legion.info.LegionTitleInfo getLegionTitleInfo(){
		return legionTitleInfo;
	}
		
	public void setLegionTitleInfo(com.hifun.soul.gameserver.legion.info.LegionTitleInfo legionTitleInfo){
		this.legionTitleInfo = legionTitleInfo;
	}

	public int getSelfMedal(){
		return selfMedal;
	}
		
	public void setSelfMedal(int selfMedal){
		this.selfMedal = selfMedal;
	}
}