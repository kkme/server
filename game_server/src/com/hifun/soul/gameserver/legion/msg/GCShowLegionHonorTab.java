package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示军团荣誉堂标签 
 *
 * @author SevenSoul
 */
@Component
public class GCShowLegionHonorTab extends GCMessage{
	
	/** 军团建筑信息 */
	private com.hifun.soul.gameserver.legion.info.LegionBuildingInfo legionBuildingInfo;
	/** 军团头衔信息 */
	private com.hifun.soul.gameserver.legion.info.LegionTitleInfo[] titleInfos;
	/** 个人勋章 */
	private int selfMedal;

	public GCShowLegionHonorTab (){
	}
	
	public GCShowLegionHonorTab (
			com.hifun.soul.gameserver.legion.info.LegionBuildingInfo legionBuildingInfo,
			com.hifun.soul.gameserver.legion.info.LegionTitleInfo[] titleInfos,
			int selfMedal ){
			this.legionBuildingInfo = legionBuildingInfo;
			this.titleInfos = titleInfos;
			this.selfMedal = selfMedal;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		legionBuildingInfo = new com.hifun.soul.gameserver.legion.info.LegionBuildingInfo();
						legionBuildingInfo.setCurrentNum(readInteger());
						legionBuildingInfo.setNextNum(readInteger());
						legionBuildingInfo.setBuildingLevel(readInteger());
						legionBuildingInfo.setNeedLegionCoin(readInteger());
						legionBuildingInfo.setCurrentLegionCoin(readInteger());
				count = readShort();
		count = count < 0 ? 0 : count;
		titleInfos = new com.hifun.soul.gameserver.legion.info.LegionTitleInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legion.info.LegionTitleInfo objtitleInfos = new com.hifun.soul.gameserver.legion.info.LegionTitleInfo();
			titleInfos[i] = objtitleInfos;
					objtitleInfos.setTitleId(readInteger());
							objtitleInfos.setTitelName(readString());
							objtitleInfos.setIconId(readInteger());
							objtitleInfos.setColorId(readInteger());
							objtitleInfos.setNeedPositionName(readString());
							objtitleInfos.setMaxNum(readInteger());
							objtitleInfos.setRemainNum(readInteger());
							objtitleInfos.setNeedMedal(readInteger());
							objtitleInfos.setIsAllProperty(readBoolean());
							objtitleInfos.setPropertyId(readInteger());
							objtitleInfos.setPropertyRate(readInteger());
							objtitleInfos.setValidDays(readInteger());
							objtitleInfos.setNeedBuildingLevel(readInteger());
				}
		selfMedal = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(legionBuildingInfo.getCurrentNum());
		writeInteger(legionBuildingInfo.getNextNum());
		writeInteger(legionBuildingInfo.getBuildingLevel());
		writeInteger(legionBuildingInfo.getNeedLegionCoin());
		writeInteger(legionBuildingInfo.getCurrentLegionCoin());
	writeShort(titleInfos.length);
	for(int i=0; i<titleInfos.length; i++){
	com.hifun.soul.gameserver.legion.info.LegionTitleInfo objtitleInfos = titleInfos[i];
				writeInteger(objtitleInfos.getTitleId());
				writeString(objtitleInfos.getTitelName());
				writeInteger(objtitleInfos.getIconId());
				writeInteger(objtitleInfos.getColorId());
				writeString(objtitleInfos.getNeedPositionName());
				writeInteger(objtitleInfos.getMaxNum());
				writeInteger(objtitleInfos.getRemainNum());
				writeInteger(objtitleInfos.getNeedMedal());
				writeBoolean(objtitleInfos.getIsAllProperty());
				writeInteger(objtitleInfos.getPropertyId());
				writeInteger(objtitleInfos.getPropertyRate());
				writeInteger(objtitleInfos.getValidDays());
				writeInteger(objtitleInfos.getNeedBuildingLevel());
	}
		writeInteger(selfMedal);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_LEGION_HONOR_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LEGION_HONOR_TAB";
	}

	public com.hifun.soul.gameserver.legion.info.LegionBuildingInfo getLegionBuildingInfo(){
		return legionBuildingInfo;
	}
		
	public void setLegionBuildingInfo(com.hifun.soul.gameserver.legion.info.LegionBuildingInfo legionBuildingInfo){
		this.legionBuildingInfo = legionBuildingInfo;
	}

	public com.hifun.soul.gameserver.legion.info.LegionTitleInfo[] getTitleInfos(){
		return titleInfos;
	}

	public void setTitleInfos(com.hifun.soul.gameserver.legion.info.LegionTitleInfo[] titleInfos){
		this.titleInfos = titleInfos;
	}	

	public int getSelfMedal(){
		return selfMedal;
	}
		
	public void setSelfMedal(int selfMedal){
		this.selfMedal = selfMedal;
	}
}