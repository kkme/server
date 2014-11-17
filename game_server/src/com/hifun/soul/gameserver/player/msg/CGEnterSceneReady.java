package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 通知服务器已经准备好进入场景
 * 
 * @author SevenSoul
 */
@Component
public class CGEnterSceneReady extends CGMessage{
	
	
	public CGEnterSceneReady (){
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
		return MessageType.CG_ENTER_SCENE_READY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ENTER_SCENE_READY";
	}

	@Override
	public void execute() {
	}
}