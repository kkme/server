package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应主人展示压榨奴隶页面
 *
 * @author SevenSoul
 */
@Component
public class GCShowPressTab extends GCMessage{
	
	/** 剩余劳作时间 */
	private int remainWorkTime;
	/** 当前劳作经验 */
	private int currentExperience;
	/** 每分钟产经验值 */
	private int expPerMinute;
	/** 提取1小时经验花费 */
	private int oneHourCost;

	public GCShowPressTab (){
	}
	
	public GCShowPressTab (
			int remainWorkTime,
			int currentExperience,
			int expPerMinute,
			int oneHourCost ){
			this.remainWorkTime = remainWorkTime;
			this.currentExperience = currentExperience;
			this.expPerMinute = expPerMinute;
			this.oneHourCost = oneHourCost;
	}

	@Override
	protected boolean readImpl() {
		remainWorkTime = readInteger();
		currentExperience = readInteger();
		expPerMinute = readInteger();
		oneHourCost = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainWorkTime);
		writeInteger(currentExperience);
		writeInteger(expPerMinute);
		writeInteger(oneHourCost);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_PRESS_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_PRESS_TAB";
	}

	public int getRemainWorkTime(){
		return remainWorkTime;
	}
		
	public void setRemainWorkTime(int remainWorkTime){
		this.remainWorkTime = remainWorkTime;
	}

	public int getCurrentExperience(){
		return currentExperience;
	}
		
	public void setCurrentExperience(int currentExperience){
		this.currentExperience = currentExperience;
	}

	public int getExpPerMinute(){
		return expPerMinute;
	}
		
	public void setExpPerMinute(int expPerMinute){
		this.expPerMinute = expPerMinute;
	}

	public int getOneHourCost(){
		return oneHourCost;
	}
		
	public void setOneHourCost(int oneHourCost){
		this.oneHourCost = oneHourCost;
	}
}