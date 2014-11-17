package com.hifun.soul.gameserver.mall.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 购买选中道具
 * 
 * @author SevenSoul
 */
@Component
public class CGAskMallItem extends CGMessage{
	
	/** 商品 Id */
	private int itemId;
	
	public CGAskMallItem (){
	}
	
	public CGAskMallItem (
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
		return MessageType.CG_ASK_MALL_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ASK_MALL_ITEM";
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