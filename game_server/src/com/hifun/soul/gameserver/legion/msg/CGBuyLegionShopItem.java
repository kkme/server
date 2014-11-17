package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求购买军团商品
 * 
 * @author SevenSoul
 */
@Component
public class CGBuyLegionShopItem extends CGMessage{
	
	/** 商品ID */
	private int itemId;
	/** 商品数量 */
	private int num;
	
	public CGBuyLegionShopItem (){
	}
	
	public CGBuyLegionShopItem (
			int itemId,
			int num ){
			this.itemId = itemId;
			this.num = num;
	}
	
	@Override
	protected boolean readImpl() {
		itemId = readInteger();
		num = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(itemId);
		writeInteger(num);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BUY_LEGION_SHOP_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BUY_LEGION_SHOP_ITEM";
	}

	public int getItemId(){
		return itemId;
	}
		
	public void setItemId(int itemId){
		this.itemId = itemId;
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