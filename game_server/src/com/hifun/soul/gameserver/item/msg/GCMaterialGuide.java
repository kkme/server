package com.hifun.soul.gameserver.item.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 材料引导
 *
 * @author SevenSoul
 */
@Component
public class GCMaterialGuide extends GCMessage{
	
	/** 材料引导 */
	private String guide;

	public GCMaterialGuide (){
	}
	
	public GCMaterialGuide (
			String guide ){
			this.guide = guide;
	}

	@Override
	protected boolean readImpl() {
		guide = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(guide);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MATERIAL_GUIDE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MATERIAL_GUIDE";
	}

	public String getGuide(){
		return guide;
	}
		
	public void setGuide(String guide){
		this.guide = guide;
	}
}