package com.hifun.soul.gameserver.bag.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 整理背包，更新背包所有物品
 * 
 * @author SevenSoul
 */
@Component
public class CGBagTidy extends CGMessage{
	
	/** 背包类型 */
	private short bagType;
	
	public CGBagTidy (){
	}
	
	public CGBagTidy (
			short bagType ){
			this.bagType = bagType;
	}
	
	@Override
	protected boolean readImpl() {
		bagType = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(bagType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_BAG_TIDY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BAG_TIDY";
	}

	public short getBagType(){
		return bagType;
	}
		
	public void setBagType(short bagType){
		this.bagType = bagType;
	}

	@Override
	public void execute() {
	}
}