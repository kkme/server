package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 开启冥想协助位
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenAssistPosition extends CGMessage{
	
	/** 是否提前开启 */
	private boolean isRichMan;
	
	public CGOpenAssistPosition (){
	}
	
	public CGOpenAssistPosition (
			boolean isRichMan ){
			this.isRichMan = isRichMan;
	}
	
	@Override
	protected boolean readImpl() {
		isRichMan = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(isRichMan);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_OPEN_ASSIST_POSITION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_ASSIST_POSITION";
	}

	public boolean getIsRichMan(){
		return isRichMan;
	}
		
	public void setIsRichMan(boolean isRichMan){
		this.isRichMan = isRichMan;
	}

	@Override
	public void execute() {
	}
}