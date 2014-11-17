package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 更新冥想面板消息
 * 
 * @author SevenSoul
 */
@Component
public class CGUpdateMeditationPannel extends CGMessage{
	
	
	public CGUpdateMeditationPannel (){
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
		return MessageType.CG_UPDATE_MEDITATION_PANNEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_UPDATE_MEDITATION_PANNEL";
	}

	@Override
	public void execute() {
	}
}