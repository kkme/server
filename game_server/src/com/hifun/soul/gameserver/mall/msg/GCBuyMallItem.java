package com.hifun.soul.gameserver.mall.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 购买选中道具结果
 *
 * @author SevenSoul
 */
@Component
public class GCBuyMallItem extends GCMessage{
	
	/** 购买结果 */
	private boolean success;

	public GCBuyMallItem (){
	}
	
	public GCBuyMallItem (
			boolean success ){
			this.success = success;
	}

	@Override
	protected boolean readImpl() {
		success = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(success);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BUY_MALL_ITEM;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BUY_MALL_ITEM";
	}

	public boolean getSuccess(){
		return success;
	}
		
	public void setSuccess(boolean success){
		this.success = success;
	}
}