package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 矿坑收获结果
 *
 * @author SevenSoul
 */
@Component
public class GCMineHarvest extends GCMessage{
	
	/** 矿坑位置索引 */
	private int index;
	/** 收获的物品名称 */
	private String itemName;
	/** 收获的物品图标id */
	private int itemIcon;
	/** 产出数量 */
	private int itemCount;

	public GCMineHarvest (){
	}
	
	public GCMineHarvest (
			int index,
			String itemName,
			int itemIcon,
			int itemCount ){
			this.index = index;
			this.itemName = itemName;
			this.itemIcon = itemIcon;
			this.itemCount = itemCount;
	}

	@Override
	protected boolean readImpl() {
		index = readInteger();
		itemName = readString();
		itemIcon = readInteger();
		itemCount = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(index);
		writeString(itemName);
		writeInteger(itemIcon);
		writeInteger(itemCount);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MINE_HARVEST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MINE_HARVEST";
	}

	public int getIndex(){
		return index;
	}
		
	public void setIndex(int index){
		this.index = index;
	}

	public String getItemName(){
		return itemName;
	}
		
	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	public int getItemIcon(){
		return itemIcon;
	}
		
	public void setItemIcon(int itemIcon){
		this.itemIcon = itemIcon;
	}

	public int getItemCount(){
		return itemCount;
	}
		
	public void setItemCount(int itemCount){
		this.itemCount = itemCount;
	}
}