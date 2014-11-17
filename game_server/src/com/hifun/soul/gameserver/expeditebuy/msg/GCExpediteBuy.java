package com.hifun.soul.gameserver.expeditebuy.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 需要购买物品的基本信息
 *
 * @author SevenSoul
 */
@Component
public class GCExpediteBuy extends GCMessage{
	
	/** 物品id */
	private int itemId;
	/** 物品名称 */
	private String name;
	/** 物品描述 */
	private String desc;
	/** 物品图标 */
	private int icon;
	/** 物品价格 */
	private int price;
	/** 物品最大可叠加数量 */
	private int maxOverlap;

	public GCExpediteBuy (){
	}
	
	public GCExpediteBuy (
			int itemId,
			String name,
			String desc,
			int icon,
			int price,
			int maxOverlap ){
			this.itemId = itemId;
			this.name = name;
			this.desc = desc;
			this.icon = icon;
			this.price = price;
			this.maxOverlap = maxOverlap;
	}

	@Override
	protected boolean readImpl() {
		itemId = readInteger();
		name = readString();
		desc = readString();
		icon = readInteger();
		price = readInteger();
		maxOverlap = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(itemId);
		writeString(name);
		writeString(desc);
		writeInteger(icon);
		writeInteger(price);
		writeInteger(maxOverlap);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_EXPEDITE_BUY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_EXPEDITE_BUY";
	}

	public int getItemId(){
		return itemId;
	}
		
	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public String getName(){
		return name;
	}
		
	public void setName(String name){
		this.name = name;
	}

	public String getDesc(){
		return desc;
	}
		
	public void setDesc(String desc){
		this.desc = desc;
	}

	public int getIcon(){
		return icon;
	}
		
	public void setIcon(int icon){
		this.icon = icon;
	}

	public int getPrice(){
		return price;
	}
		
	public void setPrice(int price){
		this.price = price;
	}

	public int getMaxOverlap(){
		return maxOverlap;
	}
		
	public void setMaxOverlap(int maxOverlap){
		this.maxOverlap = maxOverlap;
	}
}