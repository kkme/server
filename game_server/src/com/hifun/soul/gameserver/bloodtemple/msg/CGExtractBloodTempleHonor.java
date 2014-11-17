package com.hifun.soul.gameserver.bloodtemple.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求提取威望(以前是声望)
 * 
 * @author SevenSoul
 */
@Component
public class CGExtractBloodTempleHonor extends CGMessage{
	
	
	public CGExtractBloodTempleHonor (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_EXTRACT_BLOOD_TEMPLE_HONOR;
	}
	
	@Override
	public String getTypeName() {
		return "CG_EXTRACT_BLOOD_TEMPLE_HONOR";
	}

	@Override
	public void execute() {
	}
}