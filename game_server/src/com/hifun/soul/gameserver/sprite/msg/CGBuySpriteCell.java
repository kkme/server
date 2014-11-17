package com.hifun.soul.gameserver.sprite.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求购买精灵格子
 * 
 * @author SevenSoul
 */
@Component
public class CGBuySpriteCell extends CGMessage{
	
	/** 开启索引 */
	private int openIndex;
	
	public CGBuySpriteCell (){
	}
	
	public CGBuySpriteCell (
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
		return MessageType.CG_BUY_SPRITE_CELL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BUY_SPRITE_CELL";
	}

	public int getOpenIndex(){
		return openIndex;
	}
		
	public void setOpenIndex(int openIndex){
		this.openIndex = openIndex;
	}

	@Override
	public void execute() {
	}
}