package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示抓捕页面
 *
 * @author SevenSoul
 */
@Component
public class GCShowArrestTab extends GCMessage{
	
	/** 剩余抓捕次数 */
	private int remainArrestNum;
	/** 剩余解救次数 */
	private int remainRescueNum;
	/** 购买抓捕次数需要魔晶数 */
	private int buyArrestNumCost;
	/** 剩余互动次数 */
	private int remainInteractNum;
	/** 当前经验 */
	private int currentExperience;
	/** 经验限制 */
	private int experienceLimit;
	/** 手下败将  */
	private com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] loosers;

	public GCShowArrestTab (){
	}
	
	public GCShowArrestTab (
			int remainArrestNum,
			int remainRescueNum,
			int buyArrestNumCost,
			int remainInteractNum,
			int currentExperience,
			int experienceLimit,
			com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] loosers ){
			this.remainArrestNum = remainArrestNum;
			this.remainRescueNum = remainRescueNum;
			this.buyArrestNumCost = buyArrestNumCost;
			this.remainInteractNum = remainInteractNum;
			this.currentExperience = currentExperience;
			this.experienceLimit = experienceLimit;
			this.loosers = loosers;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		remainArrestNum = readInteger();
		remainRescueNum = readInteger();
		buyArrestNumCost = readInteger();
		remainInteractNum = readInteger();
		currentExperience = readInteger();
		experienceLimit = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		loosers = new com.hifun.soul.gameserver.prison.msg.PrisonerInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.prison.msg.PrisonerInfo objloosers = new com.hifun.soul.gameserver.prison.msg.PrisonerInfo();
			loosers[i] = objloosers;
					objloosers.setHumanId(readLong());
							objloosers.setHumanName(readString());
							objloosers.setHumanLevel(readInteger());
							objloosers.setIdentityType(readInteger());
							objloosers.setLegionId(readLong());
							objloosers.setLegionName(readString());
							objloosers.setOccupationType(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainArrestNum);
		writeInteger(remainRescueNum);
		writeInteger(buyArrestNumCost);
		writeInteger(remainInteractNum);
		writeInteger(currentExperience);
		writeInteger(experienceLimit);
	writeShort(loosers.length);
	for(int i=0; i<loosers.length; i++){
	com.hifun.soul.gameserver.prison.msg.PrisonerInfo objloosers = loosers[i];
				writeLong(objloosers.getHumanId());
				writeString(objloosers.getHumanName());
				writeInteger(objloosers.getHumanLevel());
				writeInteger(objloosers.getIdentityType());
				writeLong(objloosers.getLegionId());
				writeString(objloosers.getLegionName());
				writeInteger(objloosers.getOccupationType());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_ARREST_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_ARREST_TAB";
	}

	public int getRemainArrestNum(){
		return remainArrestNum;
	}
		
	public void setRemainArrestNum(int remainArrestNum){
		this.remainArrestNum = remainArrestNum;
	}

	public int getRemainRescueNum(){
		return remainRescueNum;
	}
		
	public void setRemainRescueNum(int remainRescueNum){
		this.remainRescueNum = remainRescueNum;
	}

	public int getBuyArrestNumCost(){
		return buyArrestNumCost;
	}
		
	public void setBuyArrestNumCost(int buyArrestNumCost){
		this.buyArrestNumCost = buyArrestNumCost;
	}

	public int getRemainInteractNum(){
		return remainInteractNum;
	}
		
	public void setRemainInteractNum(int remainInteractNum){
		this.remainInteractNum = remainInteractNum;
	}

	public int getCurrentExperience(){
		return currentExperience;
	}
		
	public void setCurrentExperience(int currentExperience){
		this.currentExperience = currentExperience;
	}

	public int getExperienceLimit(){
		return experienceLimit;
	}
		
	public void setExperienceLimit(int experienceLimit){
		this.experienceLimit = experienceLimit;
	}

	public com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] getLoosers(){
		return loosers;
	}

	public void setLoosers(com.hifun.soul.gameserver.prison.msg.PrisonerInfo[] loosers){
		this.loosers = loosers;
	}	
}