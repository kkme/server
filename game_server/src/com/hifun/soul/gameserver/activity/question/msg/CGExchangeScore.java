package com.hifun.soul.gameserver.activity.question.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 兑换积分请求
 * 
 * @author SevenSoul
 */
@Component
public class CGExchangeScore extends CGMessage{
	
	/** 兑换目标序号 */
	private int exchangeIndex;
	
	public CGExchangeScore (){
	}
	
	public CGExchangeScore (
			int exchangeIndex ){
			this.exchangeIndex = exchangeIndex;
	}
	
	@Override
	protected boolean readImpl() {
		exchangeIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(exchangeIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_EXCHANGE_SCORE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_EXCHANGE_SCORE";
	}

	public int getExchangeIndex(){
		return exchangeIndex;
	}
		
	public void setExchangeIndex(int exchangeIndex){
		this.exchangeIndex = exchangeIndex;
	}

	@Override
	public void execute() {
	}
}