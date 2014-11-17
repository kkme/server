package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 出拳响应
 *
 * @author SevenSoul
 */
@Component
public class GCGiveFinger extends GCMessage{
	
	/** 本回合猜拳结果(胜利-平局-失败) */
	private int result;
	/** 自己出拳类型 */
	private int yourFingerType;
	/** 精灵出拳类型 */
	private int spriteFingerType;
	/** 对酒是否结束 */
	private boolean isGuessOver;
	/** 奖励精魂类型 */
	private int rewardSoulType;
	/** 奖励精魂数量 */
	private int rewardSoulNum;
	/** 必胜消耗的魔晶数量 */
	private int succeedCrystalCost;
	/** 剩余必胜次数 */
	private int remainSucceedNum;
	/** 返回金钱 */
	private int returnMoney;

	public GCGiveFinger (){
	}
	
	public GCGiveFinger (
			int result,
			int yourFingerType,
			int spriteFingerType,
			boolean isGuessOver,
			int rewardSoulType,
			int rewardSoulNum,
			int succeedCrystalCost,
			int remainSucceedNum,
			int returnMoney ){
			this.result = result;
			this.yourFingerType = yourFingerType;
			this.spriteFingerType = spriteFingerType;
			this.isGuessOver = isGuessOver;
			this.rewardSoulType = rewardSoulType;
			this.rewardSoulNum = rewardSoulNum;
			this.succeedCrystalCost = succeedCrystalCost;
			this.remainSucceedNum = remainSucceedNum;
			this.returnMoney = returnMoney;
	}

	@Override
	protected boolean readImpl() {
		result = readInteger();
		yourFingerType = readInteger();
		spriteFingerType = readInteger();
		isGuessOver = readBoolean();
		rewardSoulType = readInteger();
		rewardSoulNum = readInteger();
		succeedCrystalCost = readInteger();
		remainSucceedNum = readInteger();
		returnMoney = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(result);
		writeInteger(yourFingerType);
		writeInteger(spriteFingerType);
		writeBoolean(isGuessOver);
		writeInteger(rewardSoulType);
		writeInteger(rewardSoulNum);
		writeInteger(succeedCrystalCost);
		writeInteger(remainSucceedNum);
		writeInteger(returnMoney);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GIVE_FINGER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GIVE_FINGER";
	}

	public int getResult(){
		return result;
	}
		
	public void setResult(int result){
		this.result = result;
	}

	public int getYourFingerType(){
		return yourFingerType;
	}
		
	public void setYourFingerType(int yourFingerType){
		this.yourFingerType = yourFingerType;
	}

	public int getSpriteFingerType(){
		return spriteFingerType;
	}
		
	public void setSpriteFingerType(int spriteFingerType){
		this.spriteFingerType = spriteFingerType;
	}

	public boolean getIsGuessOver(){
		return isGuessOver;
	}
		
	public void setIsGuessOver(boolean isGuessOver){
		this.isGuessOver = isGuessOver;
	}

	public int getRewardSoulType(){
		return rewardSoulType;
	}
		
	public void setRewardSoulType(int rewardSoulType){
		this.rewardSoulType = rewardSoulType;
	}

	public int getRewardSoulNum(){
		return rewardSoulNum;
	}
		
	public void setRewardSoulNum(int rewardSoulNum){
		this.rewardSoulNum = rewardSoulNum;
	}

	public int getSucceedCrystalCost(){
		return succeedCrystalCost;
	}
		
	public void setSucceedCrystalCost(int succeedCrystalCost){
		this.succeedCrystalCost = succeedCrystalCost;
	}

	public int getRemainSucceedNum(){
		return remainSucceedNum;
	}
		
	public void setRemainSucceedNum(int remainSucceedNum){
		this.remainSucceedNum = remainSucceedNum;
	}

	public int getReturnMoney(){
		return returnMoney;
	}
		
	public void setReturnMoney(int returnMoney){
		this.returnMoney = returnMoney;
	}
}