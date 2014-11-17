package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应购买拦截次数
 *
 * @author SevenSoul
 */
@Component
public class GCEscortBuyRobNum extends GCMessage{
	
	/** 剩余拦截次数 */
	private int remainRobNum;
	/** 购买拦截次数消费 */
	private int buyRobNumCost;

	public GCEscortBuyRobNum (){
	}
	
	public GCEscortBuyRobNum (
			int remainRobNum,
			int buyRobNumCost ){
			this.remainRobNum = remainRobNum;
			this.buyRobNumCost = buyRobNumCost;
	}

	@Override
	protected boolean readImpl() {
		remainRobNum = readInteger();
		buyRobNumCost = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainRobNum);
		writeInteger(buyRobNumCost);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ESCORT_BUY_ROB_NUM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ESCORT_BUY_ROB_NUM";
	}

	public int getRemainRobNum(){
		return remainRobNum;
	}
		
	public void setRemainRobNum(int remainRobNum){
		this.remainRobNum = remainRobNum;
	}

	public int getBuyRobNumCost(){
		return buyRobNumCost;
	}
		
	public void setBuyRobNumCost(int buyRobNumCost){
		this.buyRobNumCost = buyRobNumCost;
	}
}