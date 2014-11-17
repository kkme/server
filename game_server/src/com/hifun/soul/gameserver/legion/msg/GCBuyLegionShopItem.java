package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应购买军团商品
 *
 * @author SevenSoul
 */
@Component
public class GCBuyLegionShopItem extends GCMessage{
	
	/** 商品ID */
	private int itemId;
	/** 商品剩余数量 */
	private int itemRemainNum;
	/** 个人勋章 */
	private int selfMedal;

	public GCBuyLegionShopItem (){
	}
	
	public GCBuyLegionShopItem (
			int itemId,
			int itemRemainNum,
			int selfMedal ){
			this.itemId = itemId;
			this.itemRemainNum = itemRemainNum;
			this.selfMedal = selfMedal;
	}

	@Override
	protected boolean readImpl() {
		itemId = readInteger();
		itemRemainNum = readInteger();
		selfMedal = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(itemId);
		writeInteger(itemRemainNum);
		writeInteger(selfMedal);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BUY_LEGION_SHOP_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BUY_LEGION_SHOP_ITEM";
	}

	public int getItemId(){
		return itemId;
	}
		
	public void setItemId(int itemId){
		this.itemId = itemId;
	}

	public int getItemRemainNum(){
		return itemRemainNum;
	}
		
	public void setItemRemainNum(int itemRemainNum){
		this.itemRemainNum = itemRemainNum;
	}

	public int getSelfMedal(){
		return selfMedal;
	}
		
	public void setSelfMedal(int selfMedal){
		this.selfMedal = selfMedal;
	}
}