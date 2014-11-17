package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端通知服务端战斗资源加载完毕
 * 
 * @author SevenSoul
 */
@Component
public class CGBattleResourceLoaded extends CGMessage{
	
	
	public CGBattleResourceLoaded (){
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
		return MessageType.CG_BATTLE_RESOURCE_LOADED;
	}
	
	@Override
	public String getTypeName() {
		return "CG_BATTLE_RESOURCE_LOADED";
	}

	@Override
	public void execute() {
	}
}