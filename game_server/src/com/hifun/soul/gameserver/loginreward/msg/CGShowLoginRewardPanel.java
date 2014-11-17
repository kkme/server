package com.hifun.soul.gameserver.loginreward.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求打开连续登陆面板
 * 
 * @author SevenSoul
 */
@Component
public class CGShowLoginRewardPanel extends CGMessage{
	
	
	public CGShowLoginRewardPanel (){
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
		return MessageType.CG_SHOW_LOGIN_REWARD_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_LOGIN_REWARD_PANEL";
	}

	@Override
	public void execute() {
	}
}