package com.hifun.soul.gameserver.yellowvip.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新黄钻新手礼包领取状态
 *
 * @author SevenSoul
 */
@Component
public class GCOpenYellowVipRewardPanel extends GCMessage{
	
	/** 新手礼包领取状态(0不可领取,1可领取，2已领取) */
	private int onceRewardState;
	/** 每日礼包领取状态(0不可领取,1可领取，2已领取) */
	private int dailyRewardState;
	/** 升级礼包领取状态(0不可领取,1可领取，2已领取) */
	private int[] levelUpRewardState;
	/** 年费黄钻礼包领取状态(0不可领取,1可领取，2已领取) */
	private int yearVipRewardState;

	public GCOpenYellowVipRewardPanel (){
	}
	
	public GCOpenYellowVipRewardPanel (
			int onceRewardState,
			int dailyRewardState,
			int[] levelUpRewardState,
			int yearVipRewardState ){
			this.onceRewardState = onceRewardState;
			this.dailyRewardState = dailyRewardState;
			this.levelUpRewardState = levelUpRewardState;
			this.yearVipRewardState = yearVipRewardState;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		onceRewardState = readInteger();
		dailyRewardState = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		levelUpRewardState = new int[count];
		for(int i=0; i<count; i++){
			levelUpRewardState[i] = readInteger();
		}
		yearVipRewardState = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(onceRewardState);
		writeInteger(dailyRewardState);
	writeShort(levelUpRewardState.length);
	for(int i=0; i<levelUpRewardState.length; i++){
	Integer objlevelUpRewardState = levelUpRewardState[i];
			writeInteger(objlevelUpRewardState);
}
		writeInteger(yearVipRewardState);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_YELLOW_VIP_REWARD_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_YELLOW_VIP_REWARD_PANEL";
	}

	public int getOnceRewardState(){
		return onceRewardState;
	}
		
	public void setOnceRewardState(int onceRewardState){
		this.onceRewardState = onceRewardState;
	}

	public int getDailyRewardState(){
		return dailyRewardState;
	}
		
	public void setDailyRewardState(int dailyRewardState){
		this.dailyRewardState = dailyRewardState;
	}

	public int[] getLevelUpRewardState(){
		return levelUpRewardState;
	}

	public void setLevelUpRewardState(int[] levelUpRewardState){
		this.levelUpRewardState = levelUpRewardState;
	}	

	public int getYearVipRewardState(){
		return yearVipRewardState;
	}
		
	public void setYearVipRewardState(int yearVipRewardState){
		this.yearVipRewardState = yearVipRewardState;
	}
}