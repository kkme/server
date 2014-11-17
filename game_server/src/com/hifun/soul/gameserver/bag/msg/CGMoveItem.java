package com.hifun.soul.gameserver.bag.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 移动物品，包括物品换位置
 * 
 * @author SevenSoul
 */
@Component
public class CGMoveItem extends CGMessage{
	
	/** 来源背包类型 */
	private short fromBagType;
	/** 来源物品在背包的位置索引 */
	private int fromBagIndex;
	/** 目的背包类型 */
	private short toBagType;
	/** 目的物品在背包的位置索引 */
	private int toBagIndex;
	
	public CGMoveItem (){
	}
	
	public CGMoveItem (
			short fromBagType,
			int fromBagIndex,
			short toBagType,
			int toBagIndex ){
			this.fromBagType = fromBagType;
			this.fromBagIndex = fromBagIndex;
			this.toBagType = toBagType;
			this.toBagIndex = toBagIndex;
	}
	
	@Override
	protected boolean readImpl() {
		fromBagType = readShort();
		fromBagIndex = readInteger();
		toBagType = readShort();
		toBagIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(fromBagType);
		writeInteger(fromBagIndex);
		writeShort(toBagType);
		writeInteger(toBagIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_MOVE_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_MOVE_ITEM";
	}

	public short getFromBagType(){
		return fromBagType;
	}
		
	public void setFromBagType(short fromBagType){
		this.fromBagType = fromBagType;
	}

	public int getFromBagIndex(){
		return fromBagIndex;
	}
		
	public void setFromBagIndex(int fromBagIndex){
		this.fromBagIndex = fromBagIndex;
	}

	public short getToBagType(){
		return toBagType;
	}
		
	public void setToBagType(short toBagType){
		this.toBagType = toBagType;
	}

	public int getToBagIndex(){
		return toBagIndex;
	}
		
	public void setToBagIndex(int toBagIndex){
		this.toBagIndex = toBagIndex;
	}

	@Override
	public void execute() {
	}
}