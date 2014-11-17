package com.hifun.soul.gameserver.activity.question.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 兑换积分后的更新消息
 *
 * @author SevenSoul
 */
@Component
public class GCExchangeScore extends GCMessage{
	
	/** 兑换目标序号 */
	private int exchangeIndex;
	/** 成功(1);重复兑换(2);积分不足(3) */
	private int resultCode;
	/** 获得的金币 */
	private int coinNum;
	/** 获得的经验 */
	private int exp;
	/** 获得的科技点 */
	private int technologyPoint;
	/** 当前问答积分 */
	private int totalScore;

	public GCExchangeScore (){
	}
	
	public GCExchangeScore (
			int exchangeIndex,
			int resultCode,
			int coinNum,
			int exp,
			int technologyPoint,
			int totalScore ){
			this.exchangeIndex = exchangeIndex;
			this.resultCode = resultCode;
			this.coinNum = coinNum;
			this.exp = exp;
			this.technologyPoint = technologyPoint;
			this.totalScore = totalScore;
	}

	@Override
	protected boolean readImpl() {
		exchangeIndex = readInteger();
		resultCode = readInteger();
		coinNum = readInteger();
		exp = readInteger();
		technologyPoint = readInteger();
		totalScore = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(exchangeIndex);
		writeInteger(resultCode);
		writeInteger(coinNum);
		writeInteger(exp);
		writeInteger(technologyPoint);
		writeInteger(totalScore);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_EXCHANGE_SCORE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_EXCHANGE_SCORE";
	}

	public int getExchangeIndex(){
		return exchangeIndex;
	}
		
	public void setExchangeIndex(int exchangeIndex){
		this.exchangeIndex = exchangeIndex;
	}

	public int getResultCode(){
		return resultCode;
	}
		
	public void setResultCode(int resultCode){
		this.resultCode = resultCode;
	}

	public int getCoinNum(){
		return coinNum;
	}
		
	public void setCoinNum(int coinNum){
		this.coinNum = coinNum;
	}

	public int getExp(){
		return exp;
	}
		
	public void setExp(int exp){
		this.exp = exp;
	}

	public int getTechnologyPoint(){
		return technologyPoint;
	}
		
	public void setTechnologyPoint(int technologyPoint){
		this.technologyPoint = technologyPoint;
	}

	public int getTotalScore(){
		return totalScore;
	}
		
	public void setTotalScore(int totalScore){
		this.totalScore = totalScore;
	}
}