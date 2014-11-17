package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 关卡地图选择
 * 
 * @author SevenSoul
 */
@Component
public class CGShowStageMaps extends CGMessage{
	
	
	public CGShowStageMaps (){
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
		return MessageType.CG_SHOW_STAGE_MAPS;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_STAGE_MAPS";
	}

	@Override
	public void execute() {
	}
}