package com.hifun.soul.gameserver.foster.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 培养请求所获得的数值
 * 
 * @author SevenSoul
 */
@Component
public class CGFoster extends CGMessage{
	
	/** 培养模式id */
	private int modeId;
	
	public CGFoster (){
	}
	
	public CGFoster (
			int modeId ){
			this.modeId = modeId;
	}
	
	@Override
	protected boolean readImpl() {
		modeId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(modeId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_FOSTER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_FOSTER";
	}

	public int getModeId(){
		return modeId;
	}
		
	public void setModeId(int modeId){
		this.modeId = modeId;
	}

	@Override
	public void execute() {
	}
}