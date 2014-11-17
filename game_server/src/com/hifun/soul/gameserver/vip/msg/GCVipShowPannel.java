package com.hifun.soul.gameserver.vip.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 显示VIP面板信息
 *
 * @author SevenSoul
 */
@Component
public class GCVipShowPannel extends GCMessage{
	
	/** 当前等级 */
	private int currentLevel;
	/** 目前已经充值数量 */
	private int rechargedNum;
	/** 达到下一等级需要满足的充值数量 */
	private int nextLevelRechargeNum;
	/** 下一等级特权描述 */
	private String nextLevelDesc;
	/** vip最高等级 */
	private int maxLevel;

	public GCVipShowPannel (){
	}
	
	public GCVipShowPannel (
			int currentLevel,
			int rechargedNum,
			int nextLevelRechargeNum,
			String nextLevelDesc,
			int maxLevel ){
			this.currentLevel = currentLevel;
			this.rechargedNum = rechargedNum;
			this.nextLevelRechargeNum = nextLevelRechargeNum;
			this.nextLevelDesc = nextLevelDesc;
			this.maxLevel = maxLevel;
	}

	@Override
	protected boolean readImpl() {
		currentLevel = readInteger();
		rechargedNum = readInteger();
		nextLevelRechargeNum = readInteger();
		nextLevelDesc = readString();
		maxLevel = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(currentLevel);
		writeInteger(rechargedNum);
		writeInteger(nextLevelRechargeNum);
		writeString(nextLevelDesc);
		writeInteger(maxLevel);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_VIP_SHOW_PANNEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_VIP_SHOW_PANNEL";
	}

	public int getCurrentLevel(){
		return currentLevel;
	}
		
	public void setCurrentLevel(int currentLevel){
		this.currentLevel = currentLevel;
	}

	public int getRechargedNum(){
		return rechargedNum;
	}
		
	public void setRechargedNum(int rechargedNum){
		this.rechargedNum = rechargedNum;
	}

	public int getNextLevelRechargeNum(){
		return nextLevelRechargeNum;
	}
		
	public void setNextLevelRechargeNum(int nextLevelRechargeNum){
		this.nextLevelRechargeNum = nextLevelRechargeNum;
	}

	public String getNextLevelDesc(){
		return nextLevelDesc;
	}
		
	public void setNextLevelDesc(String nextLevelDesc){
		this.nextLevelDesc = nextLevelDesc;
	}

	public int getMaxLevel(){
		return maxLevel;
	}
		
	public void setMaxLevel(int maxLevel){
		this.maxLevel = maxLevel;
	}
}