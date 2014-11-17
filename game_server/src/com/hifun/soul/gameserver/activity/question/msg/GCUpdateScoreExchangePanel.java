package com.hifun.soul.gameserver.activity.question.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新积分兑换面板信息
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateScoreExchangePanel extends GCMessage{
	
	/** 活动描述 */
	private String description;
	/** 当前问答积分 */
	private int totalScore;
	/** 积分兑换列表 */
	private com.hifun.soul.gameserver.activity.question.model.ExchangeScore[] exchangeScoreList;

	public GCUpdateScoreExchangePanel (){
	}
	
	public GCUpdateScoreExchangePanel (
			String description,
			int totalScore,
			com.hifun.soul.gameserver.activity.question.model.ExchangeScore[] exchangeScoreList ){
			this.description = description;
			this.totalScore = totalScore;
			this.exchangeScoreList = exchangeScoreList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		description = readString();
		totalScore = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		exchangeScoreList = new com.hifun.soul.gameserver.activity.question.model.ExchangeScore[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.activity.question.model.ExchangeScore objexchangeScoreList = new com.hifun.soul.gameserver.activity.question.model.ExchangeScore();
			exchangeScoreList[i] = objexchangeScoreList;
					objexchangeScoreList.setId(readInteger());
							objexchangeScoreList.setScore(readInteger());
							objexchangeScoreList.setExchangeState(readInteger());
							objexchangeScoreList.setCoin(readInteger());
							objexchangeScoreList.setExp(readInteger());
							objexchangeScoreList.setTechPoint(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(description);
		writeInteger(totalScore);
	writeShort(exchangeScoreList.length);
	for(int i=0; i<exchangeScoreList.length; i++){
	com.hifun.soul.gameserver.activity.question.model.ExchangeScore objexchangeScoreList = exchangeScoreList[i];
				writeInteger(objexchangeScoreList.getId());
				writeInteger(objexchangeScoreList.getScore());
				writeInteger(objexchangeScoreList.getExchangeState());
				writeInteger(objexchangeScoreList.getCoin());
				writeInteger(objexchangeScoreList.getExp());
				writeInteger(objexchangeScoreList.getTechPoint());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_SCORE_EXCHANGE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_SCORE_EXCHANGE_PANEL";
	}

	public String getDescription(){
		return description;
	}
		
	public void setDescription(String description){
		this.description = description;
	}

	public int getTotalScore(){
		return totalScore;
	}
		
	public void setTotalScore(int totalScore){
		this.totalScore = totalScore;
	}

	public com.hifun.soul.gameserver.activity.question.model.ExchangeScore[] getExchangeScoreList(){
		return exchangeScoreList;
	}

	public void setExchangeScoreList(com.hifun.soul.gameserver.activity.question.model.ExchangeScore[] exchangeScoreList){
		this.exchangeScoreList = exchangeScoreList;
	}	
}