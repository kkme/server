package com.hifun.soul.gameserver.mall.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 显示选中道具
 * 
 * @author SevenSoul
 */
@Component
public class CGShowMallItem extends CGMessage{
	
	/** 商品 Id */
	private int itemId;
	
	public CGShowMallItem (){
	}
	
	public CGShowMallItem (
			int itemId ){
			this.itemId = itemId;
	}
	
	@Override
	protected boolean readImpl() {
		itemId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(itemId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_MALL_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_MALL_ITEM";
	}

	public int getItemId(){
		return itemId;
	}
		
	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	@Override
	public void execute() {
	}
}