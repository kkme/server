package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示军团科技厅标签 
 *
 * @author SevenSoul
 */
@Component
public class GCShowLegionTechnologyTab extends GCMessage{
	
	/** 军团建筑信息 */
	private com.hifun.soul.gameserver.legion.info.LegionBuildingInfo legionBuildingInfo;
	/** 贡献消费金币 */
	private int contributeCostCoin;
	/** 军团科技信息 */
	private com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo[] technologyInfos;
	/** 个人勋章 */
	private int selfMedal;

	public GCShowLegionTechnologyTab (){
	}
	
	public GCShowLegionTechnologyTab (
			com.hifun.soul.gameserver.legion.info.LegionBuildingInfo legionBuildingInfo,
			int contributeCostCoin,
			com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo[] technologyInfos,
			int selfMedal ){
			this.legionBuildingInfo = legionBuildingInfo;
			this.contributeCostCoin = contributeCostCoin;
			this.technologyInfos = technologyInfos;
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
				contributeCostCoin = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		technologyInfos = new com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo objtechnologyInfos = new com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo();
			technologyInfos[i] = objtechnologyInfos;
					objtechnologyInfos.setTechnologyType(readInteger());
							objtechnologyInfos.setTechnologyName(readString());
							objtechnologyInfos.setIconId(readInteger());
							objtechnologyInfos.setAmendDesc(readString());
							objtechnologyInfos.setTechnologyLevel(readInteger());
							objtechnologyInfos.setUpNeedCoin(readInteger());
							objtechnologyInfos.setUpCurrentCoin(readInteger());
							objtechnologyInfos.setAmendEffect(readInteger());
							objtechnologyInfos.setAmendMethod(readInteger());
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
		writeInteger(contributeCostCoin);
	writeShort(technologyInfos.length);
	for(int i=0; i<technologyInfos.length; i++){
	com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo objtechnologyInfos = technologyInfos[i];
				writeInteger(objtechnologyInfos.getTechnologyType());
				writeString(objtechnologyInfos.getTechnologyName());
				writeInteger(objtechnologyInfos.getIconId());
				writeString(objtechnologyInfos.getAmendDesc());
				writeInteger(objtechnologyInfos.getTechnologyLevel());
				writeInteger(objtechnologyInfos.getUpNeedCoin());
				writeInteger(objtechnologyInfos.getUpCurrentCoin());
				writeInteger(objtechnologyInfos.getAmendEffect());
				writeInteger(objtechnologyInfos.getAmendMethod());
	}
		writeInteger(selfMedal);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_LEGION_TECHNOLOGY_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LEGION_TECHNOLOGY_TAB";
	}

	public com.hifun.soul.gameserver.legion.info.LegionBuildingInfo getLegionBuildingInfo(){
		return legionBuildingInfo;
	}
		
	public void setLegionBuildingInfo(com.hifun.soul.gameserver.legion.info.LegionBuildingInfo legionBuildingInfo){
		this.legionBuildingInfo = legionBuildingInfo;
	}

	public int getContributeCostCoin(){
		return contributeCostCoin;
	}
		
	public void setContributeCostCoin(int contributeCostCoin){
		this.contributeCostCoin = contributeCostCoin;
	}

	public com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo[] getTechnologyInfos(){
		return technologyInfos;
	}

	public void setTechnologyInfos(com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo[] technologyInfos){
		this.technologyInfos = technologyInfos;
	}	

	public int getSelfMedal(){
		return selfMedal;
	}
		
	public void setSelfMedal(int selfMedal){
		this.selfMedal = selfMedal;
	}
}