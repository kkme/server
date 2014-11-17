package com.hifun.soul.gameserver.elitestage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开精英副本面板
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenEliteStagePanel extends CGMessage{
	
	
	public CGOpenEliteStagePanel (){
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
		return MessageType.CG_OPEN_ELITE_STAGE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_ELITE_STAGE_PANEL";
	}

	@Override
	public void execute() {
	}
}