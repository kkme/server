package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应捐献军团科技 
 *
 * @author SevenSoul
 */
@Component
public class GCContributeLegionTechnology extends GCMessage{
	
	/** 军团科技信息 */
	private com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo technologyInfo;
	/** 个人勋章 */
	private int selfMedal;

	public GCContributeLegionTechnology (){
	}
	
	public GCContributeLegionTechnology (
			com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo technologyInfo,
			int selfMedal ){
			this.technologyInfo = technologyInfo;
			this.selfMedal = selfMedal;
	}

	@Override
	protected boolean readImpl() {
		technologyInfo = new com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo();
						technologyInfo.setTechnologyType(readInteger());
						technologyInfo.setTechnologyName(readString());
						technologyInfo.setIconId(readInteger());
						technologyInfo.setAmendDesc(readString());
						technologyInfo.setTechnologyLevel(readInteger());
						technologyInfo.setUpNeedCoin(readInteger());
						technologyInfo.setUpCurrentCoin(readInteger());
						technologyInfo.setAmendEffect(readInteger());
						technologyInfo.setAmendMethod(readInteger());
				selfMedal = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(technologyInfo.getTechnologyType());
		writeString(technologyInfo.getTechnologyName());
		writeInteger(technologyInfo.getIconId());
		writeString(technologyInfo.getAmendDesc());
		writeInteger(technologyInfo.getTechnologyLevel());
		writeInteger(technologyInfo.getUpNeedCoin());
		writeInteger(technologyInfo.getUpCurrentCoin());
		writeInteger(technologyInfo.getAmendEffect());
		writeInteger(technologyInfo.getAmendMethod());
		writeInteger(selfMedal);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CONTRIBUTE_LEGION_TECHNOLOGY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CONTRIBUTE_LEGION_TECHNOLOGY";
	}

	public com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo getTechnologyInfo(){
		return technologyInfo;
	}
		
	public void setTechnologyInfo(com.hifun.soul.gameserver.legion.info.LegionTechnologyInfo technologyInfo){
		this.technologyInfo = technologyInfo;
	}

	public int getSelfMedal(){
		return selfMedal;
	}
		
	public void setSelfMedal(int selfMedal){
		this.selfMedal = selfMedal;
	}
}