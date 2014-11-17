package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 材料引导
 * 
 * @author SevenSoul
 */
@Component
public class CGMaterialGuide extends CGMessage{
	
	/** 材料id */
	private int itemId;
	
	public CGMaterialGuide (){
	}
	
	public CGMaterialGuide (
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
		return MessageType.CG_MATERIAL_GUIDE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_MATERIAL_GUIDE";
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