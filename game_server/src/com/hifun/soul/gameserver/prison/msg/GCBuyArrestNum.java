package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应购买抓捕次数
 *
 * @author SevenSoul
 */
@Component
public class GCBuyArrestNum extends GCMessage{
	
	/** 剩余抓捕次数 */
	private int remainArrestNum;
	/** 下次购买需要魔晶数 */
	private int nextBuyCost;

	public GCBuyArrestNum (){
	}
	
	public GCBuyArrestNum (
			int remainArrestNum,
			int nextBuyCost ){
			this.remainArrestNum = remainArrestNum;
			this.nextBuyCost = nextBuyCost;
	}

	@Override
	protected boolean readImpl() {
		remainArrestNum = readInteger();
		nextBuyCost = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainArrestNum);
		writeInteger(nextBuyCost);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BUY_ARREST_NUM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BUY_ARREST_NUM";
	}

	public int getRemainArrestNum(){
		return remainArrestNum;
	}
		
	public void setRemainArrestNum(int remainArrestNum){
		this.remainArrestNum = remainArrestNum;
	}

	public int getNextBuyCost(){
		return nextBuyCost;
	}
		
	public void setNextBuyCost(int nextBuyCost){
		this.nextBuyCost = nextBuyCost;
	}
}