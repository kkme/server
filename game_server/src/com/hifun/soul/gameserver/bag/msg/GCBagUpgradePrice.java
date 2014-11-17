package com.hifun.soul.gameserver.bag.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器返回升级背包价格
 *
 * @author SevenSoul
 */
@Component
public class GCBagUpgradePrice extends GCMessage{
	
	/** 货币类型 */
	private short currencyType;
	/** 货币数量 */
	private int price;

	public GCBagUpgradePrice (){
	}
	
	public GCBagUpgradePrice (
			short currencyType,
			int price ){
			this.currencyType = currencyType;
			this.price = price;
	}

	@Override
	protected boolean readImpl() {
		currencyType = readShort();
		price = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(currencyType);
		writeInteger(price);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BAG_UPGRADE_PRICE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BAG_UPGRADE_PRICE";
	}

	public short getCurrencyType(){
		return currencyType;
	}
		
	public void setCurrencyType(short currencyType){
		this.currencyType = currencyType;
	}

	public int getPrice(){
		return price;
	}
		
	public void setPrice(int price){
		this.price = price;
	}
}