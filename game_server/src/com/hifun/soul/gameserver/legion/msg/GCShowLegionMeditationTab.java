package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示冥想殿标签 
 *
 * @author SevenSoul
 */
@Component
public class GCShowLegionMeditationTab extends GCMessage{
	
	/** 军团建筑信息 */
	private com.hifun.soul.gameserver.legion.info.LegionBuildingInfo legionBuildingInfo;
	/** 是否已冥想 */
	private boolean isMeditationed;
	/** 军团冥想殿信息 */
	private com.hifun.soul.gameserver.legion.info.LegionMeditationInfo[] meditationInfos;
	/** 军团冥想日志信息 */
	private String[] meditationLogs;

	public GCShowLegionMeditationTab (){
	}
	
	public GCShowLegionMeditationTab (
			com.hifun.soul.gameserver.legion.info.LegionBuildingInfo legionBuildingInfo,
			boolean isMeditationed,
			com.hifun.soul.gameserver.legion.info.LegionMeditationInfo[] meditationInfos,
			String[] meditationLogs ){
			this.legionBuildingInfo = legionBuildingInfo;
			this.isMeditationed = isMeditationed;
			this.meditationInfos = meditationInfos;
			this.meditationLogs = meditationLogs;
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
				isMeditationed = readBoolean();
		count = readShort();
		count = count < 0 ? 0 : count;
		meditationInfos = new com.hifun.soul.gameserver.legion.info.LegionMeditationInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legion.info.LegionMeditationInfo objmeditationInfos = new com.hifun.soul.gameserver.legion.info.LegionMeditationInfo();
			meditationInfos[i] = objmeditationInfos;
					objmeditationInfos.setMeditationType(readInteger());
							objmeditationInfos.setMeditationName(readString());
							objmeditationInfos.setVipLevel(readInteger());
							objmeditationInfos.setCurrencyType(readInteger());
							objmeditationInfos.setCurrencyNum(readInteger());
							objmeditationInfos.setMeditation(readInteger());
							objmeditationInfos.setMedal(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		meditationLogs = new String[count];
		for(int i=0; i<count; i++){
			meditationLogs[i] = readString();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(legionBuildingInfo.getCurrentNum());
		writeInteger(legionBuildingInfo.getNextNum());
		writeInteger(legionBuildingInfo.getBuildingLevel());
		writeInteger(legionBuildingInfo.getNeedLegionCoin());
		writeInteger(legionBuildingInfo.getCurrentLegionCoin());
		writeBoolean(isMeditationed);
	writeShort(meditationInfos.length);
	for(int i=0; i<meditationInfos.length; i++){
	com.hifun.soul.gameserver.legion.info.LegionMeditationInfo objmeditationInfos = meditationInfos[i];
				writeInteger(objmeditationInfos.getMeditationType());
				writeString(objmeditationInfos.getMeditationName());
				writeInteger(objmeditationInfos.getVipLevel());
				writeInteger(objmeditationInfos.getCurrencyType());
				writeInteger(objmeditationInfos.getCurrencyNum());
				writeInteger(objmeditationInfos.getMeditation());
				writeInteger(objmeditationInfos.getMedal());
	}
	writeShort(meditationLogs.length);
	for(int i=0; i<meditationLogs.length; i++){
	String objmeditationLogs = meditationLogs[i];
			writeString(objmeditationLogs);
}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_LEGION_MEDITATION_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LEGION_MEDITATION_TAB";
	}

	public com.hifun.soul.gameserver.legion.info.LegionBuildingInfo getLegionBuildingInfo(){
		return legionBuildingInfo;
	}
		
	public void setLegionBuildingInfo(com.hifun.soul.gameserver.legion.info.LegionBuildingInfo legionBuildingInfo){
		this.legionBuildingInfo = legionBuildingInfo;
	}

	public boolean getIsMeditationed(){
		return isMeditationed;
	}
		
	public void setIsMeditationed(boolean isMeditationed){
		this.isMeditationed = isMeditationed;
	}

	public com.hifun.soul.gameserver.legion.info.LegionMeditationInfo[] getMeditationInfos(){
		return meditationInfos;
	}

	public void setMeditationInfos(com.hifun.soul.gameserver.legion.info.LegionMeditationInfo[] meditationInfos){
		this.meditationInfos = meditationInfos;
	}	

	public String[] getMeditationLogs(){
		return meditationLogs;
	}

	public void setMeditationLogs(String[] meditationLogs){
		this.meditationLogs = meditationLogs;
	}	
}