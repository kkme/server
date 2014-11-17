package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端通知服务器动画播放完了
 * 
 * @author SevenSoul
 */
@Component
public class CGAnimationOver extends CGMessage{
	
	
	public CGAnimationOver (){
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
		return MessageType.CG_ANIMATION_OVER;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ANIMATION_OVER";
	}

	@Override
	public void execute() {
	}
}