package com.hifun.soul.gameserver.expeditebuy.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 快捷购买
 * 
 * @author SevenSoul
 */
@Component
public class CGExpediteBuy extends CGMessage{
	
	/** 物品id */
	private int itemId;
	
	public CGExpediteBuy (){
	}
	
	public CGExpediteBuy (
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
		return MessageType.CG_EXPEDITE_BUY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_EXPEDITE_BUY";
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