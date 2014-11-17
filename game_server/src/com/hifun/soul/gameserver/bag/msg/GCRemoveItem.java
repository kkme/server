package com.hifun.soul.gameserver.bag.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器返回移除物品结果
 *
 * @author SevenSoul
 */
@Component
public class GCRemoveItem extends GCMessage{
	
	/** 背包类型 */
	private short bagType;
	/** 物品在背包的位置索引 */
	private int bagIndex;

	public GCRemoveItem (){
	}
	
	public GCRemoveItem (
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
		return MessageType.GC_REMOVE_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REMOVE_ITEM";
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
}