package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求开始押运 
 * 
 * @author SevenSoul
 */
@Component
public class CGStartEscort extends CGMessage{
	
	/** 是否鼓舞 */
	private boolean isEncouraged;
	
	public CGStartEscort (){
	}
	
	public CGStartEscort (
			boolean isEncouraged ){
			this.isEncouraged = isEncouraged;
	}
	
	@Override
	protected boolean readImpl() {
		isEncouraged = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(isEncouraged);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_START_ESCORT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_START_ESCORT";
	}

	public boolean getIsEncouraged(){
		return isEncouraged;
	}
		
	public void setIsEncouraged(boolean isEncouraged){
		this.isEncouraged = isEncouraged;
	}

	@Override
	public void execute() {
	}
}