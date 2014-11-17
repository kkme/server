package com.hifun.soul.gameserver.activity.question.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回购买祈福花费
 *
 * @author SevenSoul
 */
@Component
public class GCBuyBlessCost extends GCMessage{
	
	/** 答案序号 */
	private int answerIndex;
	/** 花费货币类型 */
	private int currencyType;
	/** 花费数量 */
	private int costNum;

	public GCBuyBlessCost (){
	}
	
	public GCBuyBlessCost (
			int answerIndex,
			int currencyType,
			int costNum ){
			this.answerIndex = answerIndex;
			this.currencyType = currencyType;
			this.costNum = costNum;
	}

	@Override
	protected boolean readImpl() {
		answerIndex = readInteger();
		currencyType = readInteger();
		costNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(answerIndex);
		writeInteger(currencyType);
		writeInteger(costNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BUY_BLESS_COST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BUY_BLESS_COST";
	}

	public int getAnswerIndex(){
		return answerIndex;
	}
		
	public void setAnswerIndex(int answerIndex){
		this.answerIndex = answerIndex;
	}

	public int getCurrencyType(){
		return currencyType;
	}
		
	public void setCurrencyType(int currencyType){
		this.currencyType = currencyType;
	}

	public int getCostNum(){
		return costNum;
	}
		
	public void setCostNum(int costNum){
		this.costNum = costNum;
	}
}