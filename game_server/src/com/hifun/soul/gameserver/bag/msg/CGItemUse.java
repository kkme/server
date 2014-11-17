package com.hifun.soul.gameserver.bag.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 使用物品
 * 
 * @author SevenSoul
 */
@Component
public class CGItemUse extends CGMessage{
	
	/** 背包类型 */
	private short bagType;
	/** 物品所在背包索引 */
	private int bagIndex;
	
	public CGItemUse (){
	}
	
	public CGItemUse (
			short bagType,
			int bagIndex ){
			this.bagType = bagType;
			this.bagIndex = bagIndex;
	}
	
	@Override
	protected boolean readImpl() {
		bagType = readShort();
		bagIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bagType);
		writeInteger(bagIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ITEM_USE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ITEM_USE";
	}

	public short getBagType(){
		return bagType;
	}
		
	public void setBagType(short bagType){
		this.bagType = bagType;
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