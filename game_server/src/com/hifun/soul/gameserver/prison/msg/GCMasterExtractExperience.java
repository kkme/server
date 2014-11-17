package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应主人提取奴隶经验
 *
 * @author SevenSoul
 */
@Component
public class GCMasterExtractExperience extends GCMessage{
	
	/** 提取类型 */
	private int extractType;

	public GCMasterExtractExperience (){
	}
	
	public GCMasterExtractExperience (
			int extractType ){
			this.extractType = extractType;
	}

	@Override
	protected boolean readImpl() {
		extractType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(extractType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MASTER_EXTRACT_EXPERIENCE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MASTER_EXTRACT_EXPERIENCE";
	}

	public int getExtractType(){
		return extractType;
	}
		
	public void setExtractType(int extractType){
		this.extractType = extractType;
	}
}