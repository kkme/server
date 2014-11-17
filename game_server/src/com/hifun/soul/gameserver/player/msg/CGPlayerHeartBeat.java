package com.hifun.soul.gameserver.player.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 玩家心跳包，防止长时间不活动被TGW(server)踢掉
 * 
 * @author SevenSoul
 */
@Component
public class CGPlayerHeartBeat extends CGMessage{
	
	
	public CGPlayerHeartBeat (){
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
		return MessageType.CG_PLAYER_HEART_BEAT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_PLAYER_HEART_BEAT";
	}

	@Override
	public void execute() {
	}
}