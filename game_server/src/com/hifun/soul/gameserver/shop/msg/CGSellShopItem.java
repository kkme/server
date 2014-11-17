package com.hifun.soul.gameserver.shop.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 出售选中道具
 * 
 * @author SevenSoul
 */
@Component
public class CGSellShopItem extends CGMessage{
	
	/** 物品背包类型 */
	private int bagType;
	/** 物品背包索引 */
	private int bagIndex;
	/** 出售数量 */
	private int num;
	
	public CGSellShopItem (){
	}
	
	public CGSellShopItem (
			int bagType,
			int bagIndex,
			int num ){
			this.bagType = bagType;
			this.bagIndex = bagIndex;
			this.num = num;
	}
	
	@Override
	protected boolean readImpl() {
		bagType = readInteger();
		bagIndex = readInteger();
		num = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bagType);
		writeInteger(bagIndex);
		writeInteger(num);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SELL_SHOP_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SELL_SHOP_ITEM";
	}

	public int getBagType(){
		return bagType;
	}
		
	public void setBagType(int bagType){
		this.bagType = bagType;
	}

	public int getBagIndex(){
		return bagIndex;
	}
		
	public void setBagIndex(int bagIndex){
		this.bagIndex = bagIndex;
	}

	public int getNum(){
		return num;
	}
		
	public void setNum(int num){
		this.num = num;
	}

	@Override
	public void execute() {
	}
}