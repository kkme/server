package com.hifun.soul.gameserver.shop.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 购买神秘商店物品
 * 
 * @author SevenSoul
 */
@Component
public class CGBuySpecialShopItem extends CGMessage{
	
	/** 神秘商品id */
	private int specialShopItemId;
	
	public CGBuySpecialShopItem (){
	}
	
	public CGBuySpecialShopItem (
			int specialShopItemId ){
			this.specialShopItemId = specialShopItemId;
	}
	
	@Override
	protected boolean readImpl() {
		specialShopItemId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(specialShopItemId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BUY_SPECIAL_SHOP_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BUY_SPECIAL_SHOP_ITEM";
	}

	public int getSpecialShopItemId(){
		return specialShopItemId;
	}
		
	public void setSpecialShopItemId(int specialShopItemId){
		this.specialShopItemId = specialShopItemId;
	}

	@Override
	public void execute() {
	}
}