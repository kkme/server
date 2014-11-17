package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 宝石成功卸下
 *
 * @author SevenSoul
 */
@Component
public class GCGemExtract extends GCMessage{
	
	/** 宝石在孔的位置 */
	private short gemIndex;

	public GCGemExtract (){
	}
	
	public GCGemExtract (
			short gemIndex ){
			this.gemIndex = gemIndex;
	}

	@Override
	protected boolean readImpl() {
		gemIndex = readShort();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(gemIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GEM_EXTRACT;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GEM_EXTRACT";
	}

	public short getGemIndex(){
		return gemIndex;
	}
		
	public void setGemIndex(short gemIndex){
		this.gemIndex = gemIndex;
	}
}