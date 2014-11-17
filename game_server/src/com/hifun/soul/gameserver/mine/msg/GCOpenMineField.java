package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 开启矿坑结果
 *
 * @author SevenSoul
 */
@Component
public class GCOpenMineField extends GCMessage{
	
	/** 是否挑战 */
	private boolean challenge;
	/** 挑战结果 */
	private boolean challengeResult;
	/** 矿坑信息 */
	private com.hifun.soul.gameserver.mine.MineFieldInfo mineField;
	/** 购买开采次数消耗货币类型 */
	private int buyMineTimeCostType;
	/** 购买开采次数消耗货币数量 */
	private int buyMineTimeCostNum;
	/** 还可购买的开采次数 */
	private int canBuyNum;

	public GCOpenMineField (){
	}
	
	public GCOpenMineField (
			boolean challenge,
			boolean challengeResult,
			com.hifun.soul.gameserver.mine.MineFieldInfo mineField,
			int buyMineTimeCostType,
			int buyMineTimeCostNum,
			int canBuyNum ){
			this.challenge = challenge;
			this.challengeResult = challengeResult;
			this.mineField = mineField;
			this.buyMineTimeCostType = buyMineTimeCostType;
			this.buyMineTimeCostNum = buyMineTimeCostNum;
			this.canBuyNum = canBuyNum;
	}

	@Override
	protected boolean readImpl() {
		challenge = readBoolean();
		challengeResult = readBoolean();
		mineField = new com.hifun.soul.gameserver.mine.MineFieldInfo();
						mineField.setIndex(readInteger());
						mineField.setType(readInteger());
						mineField.setName(readString());
						mineField.setDesc(readString());
						mineField.setPicId(readInteger());
						mineField.setIsBadMineField(readBoolean());
				buyMineTimeCostType = readInteger();
		buyMineTimeCostNum = readInteger();
		canBuyNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(challenge);
		writeBoolean(challengeResult);
		writeInteger(mineField.getIndex());
		writeInteger(mineField.getType());
		writeString(mineField.getName());
		writeString(mineField.getDesc());
		writeInteger(mineField.getPicId());
		writeBoolean(mineField.getIsBadMineField());
		writeInteger(buyMineTimeCostType);
		writeInteger(buyMineTimeCostNum);
		writeInteger(canBuyNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_MINE_FIELD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_MINE_FIELD";
	}

	public boolean getChallenge(){
		return challenge;
	}
		
	public void setChallenge(boolean challenge){
		this.challenge = challenge;
	}

	public boolean getChallengeResult(){
		return challengeResult;
	}
		
	public void setChallengeResult(boolean challengeResult){
		this.challengeResult = challengeResult;
	}

	public com.hifun.soul.gameserver.mine.MineFieldInfo getMineField(){
		return mineField;
	}
		
	public void setMineField(com.hifun.soul.gameserver.mine.MineFieldInfo mineField){
		this.mineField = mineField;
	}

	public int getBuyMineTimeCostType(){
		return buyMineTimeCostType;
	}
		
	public void setBuyMineTimeCostType(int buyMineTimeCostType){
		this.buyMineTimeCostType = buyMineTimeCostType;
	}

	public int getBuyMineTimeCostNum(){
		return buyMineTimeCostNum;
	}
		
	public void setBuyMineTimeCostNum(int buyMineTimeCostNum){
		this.buyMineTimeCostNum = buyMineTimeCostNum;
	}

	public int getCanBuyNum(){
		return canBuyNum;
	}
		
	public void setCanBuyNum(int canBuyNum){
		this.canBuyNum = canBuyNum;
	}
}