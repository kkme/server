package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 购买精灵格子响应 
 *
 * @author SevenSoul
 */
@Component
public class GCBuySpriteCell extends GCMessage{
	
	/** 开启索引 */
	private int openIndex;

	public GCBuySpriteCell (){
	}
	
	public GCBuySpriteCell (
			int openIndex ){
			this.openIndex = openIndex;
	}

	@Override
	protected boolean readImpl() {
		openIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(openIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BUY_SPRITE_CELL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BUY_SPRITE_CELL";
	}

	public int getOpenIndex(){
		return openIndex;
	}
		
	public void setOpenIndex(int openIndex){
		this.openIndex = openIndex;
	}
}