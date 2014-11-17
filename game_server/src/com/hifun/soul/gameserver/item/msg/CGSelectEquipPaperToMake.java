package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 选择装备图纸用于打造装备
 * 
 * @author SevenSoul
 */
@Component
public class CGSelectEquipPaperToMake extends CGMessage{
	
	/** 装备图纸所在背包索引 */
	private int bagIndex;
	
	public CGSelectEquipPaperToMake (){
	}
	
	public CGSelectEquipPaperToMake (
			int bagIndex ){
			this.bagIndex = bagIndex;
	}
	
	@Override
	protected boolean readImpl() {
		bagIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(bagIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SELECT_EQUIP_PAPER_TO_MAKE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SELECT_EQUIP_PAPER_TO_MAKE";
	}

	public int getBagIndex(){
		return bagIndex;
	}
		
	public void setBagIndex(int bagIndex){
		this.bagIndex = bagIndex;
	}

	@Override
	public void execute() {
	}
}