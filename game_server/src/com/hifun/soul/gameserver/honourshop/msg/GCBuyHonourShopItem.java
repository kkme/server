package com.hifun.soul.gameserver.honourshop.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 购买选中道具结果
 *
 * @author SevenSoul
 */
@Component
public class GCBuyHonourShopItem extends GCMessage{
	
	/** 购买结果 */
	private boolean success;
	/** 剩余的荣誉 */
	private int leftHonour;

	public GCBuyHonourShopItem (){
	}
	
	public GCBuyHonourShopItem (
			boolean success,
			int leftHonour ){
			this.success = success;
			this.leftHonour = leftHonour;
	}

	@Override
	protected boolean readImpl() {
		success = readBoolean();
		leftHonour = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(success);
		writeInteger(leftHonour);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BUY_HONOUR_SHOP_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BUY_HONOUR_SHOP_ITEM";
	}

	public boolean getSuccess(){
		return success;
	}
		
	public void setSuccess(boolean success){
		this.success = success;
	}

	public int getLeftHonour(){
		return leftHonour;
	}
		
	public void setLeftHonour(int leftHonour){
		this.leftHonour = leftHonour;
	}
}