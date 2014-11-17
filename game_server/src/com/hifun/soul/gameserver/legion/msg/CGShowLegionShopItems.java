package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求展示军团某一类型商品
 * 
 * @author SevenSoul
 */
@Component
public class CGShowLegionShopItems extends CGMessage{
	
	/** 商品标签类型 */
	private int itemType;
	
	public CGShowLegionShopItems (){
	}
	
	public CGShowLegionShopItems (
			int itemType ){
			this.itemType = itemType;
	}
	
	@Override
	protected boolean readImpl() {
		itemType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(itemType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_LEGION_SHOP_ITEMS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_LEGION_SHOP_ITEMS";
	}

	public int getItemType(){
		return itemType;
	}
		
	public void setItemType(int itemType){
		this.itemType = itemType;
	}

	@Override
	public void execute() {
	}
}