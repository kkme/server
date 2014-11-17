package com.hifun.soul.gameserver.mall.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 购买选中道具结果
 *
 * @author SevenSoul
 */
@Component
public class GCAskMallItem extends GCMessage{
	
	/** 货币类型 */
	private short currencyType;
	/** 货币数量 */
	private int currencyNum;

	public GCAskMallItem (){
	}
	
	public GCAskMallItem (
			short currencyType,
			int currencyNum ){
			this.currencyType = currencyType;
			this.currencyNum = currencyNum;
	}

	@Override
	protected boolean readImpl() {
		currencyType = readShort();
		currencyNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(currencyType);
		writeInteger(currencyNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ASK_MALL_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ASK_MALL_ITEM";
	}

	public short getCurrencyType(){
		return currencyType;
	}
		
	public void setCurrencyType(short currencyType){
		this.currencyType = currencyType;
	}

	public int getCurrencyNum(){
		return currencyNum;
	}
		
	public void setCurrencyNum(int currencyNum){
		this.currencyNum = currencyNum;
	}
}