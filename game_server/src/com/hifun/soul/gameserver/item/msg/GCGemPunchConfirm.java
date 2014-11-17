package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 装备打孔花费确认
 *
 * @author SevenSoul
 */
@Component
public class GCGemPunchConfirm extends GCMessage{
	
	/** 消耗道具名称 */
	private String itemName;
	/** 消耗道具数量 */
	private int itemNum;

	public GCGemPunchConfirm (){
	}
	
	public GCGemPunchConfirm (
			String itemName,
			int itemNum ){
			this.itemName = itemName;
			this.itemNum = itemNum;
	}

	@Override
	protected boolean readImpl() {
		itemName = readString();
		itemNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(itemName);
		writeInteger(itemNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GEM_PUNCH_CONFIRM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GEM_PUNCH_CONFIRM";
	}

	public String getItemName(){
		return itemName;
	}
		
	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	public int getItemNum(){
		return itemNum;
	}
		
	public void setItemNum(int itemNum){
		this.itemNum = itemNum;
	}
}