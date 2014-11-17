package com.hifun.soul.gameserver.mars.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应购买加倍次数
 *
 * @author SevenSoul
 */
@Component
public class GCBuyMarsMultipleNum extends GCMessage{
	
	/** 剩余加倍次数 */
	private int remainMultipleNum;
	/** 购买加倍次数花费 */
	private int buyMultipleNumCost;

	public GCBuyMarsMultipleNum (){
	}
	
	public GCBuyMarsMultipleNum (
			int remainMultipleNum,
			int buyMultipleNumCost ){
			this.remainMultipleNum = remainMultipleNum;
			this.buyMultipleNumCost = buyMultipleNumCost;
	}

	@Override
	protected boolean readImpl() {
		remainMultipleNum = readInteger();
		buyMultipleNumCost = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainMultipleNum);
		writeInteger(buyMultipleNumCost);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BUY_MARS_MULTIPLE_NUM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BUY_MARS_MULTIPLE_NUM";
	}

	public int getRemainMultipleNum(){
		return remainMultipleNum;
	}
		
	public void setRemainMultipleNum(int remainMultipleNum){
		this.remainMultipleNum = remainMultipleNum;
	}

	public int getBuyMultipleNumCost(){
		return buyMultipleNumCost;
	}
		
	public void setBuyMultipleNumCost(int buyMultipleNumCost){
		this.buyMultipleNumCost = buyMultipleNumCost;
	}
}