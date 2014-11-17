package com.hifun.soul.gameserver.legionboss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求进入军团boss战
 * 
 * @author SevenSoul
 */
@Component
public class CGEnterLegionBossWar extends CGMessage{
	
	
	public CGEnterLegionBossWar (){
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
		return MessageType.CG_ENTER_LEGION_BOSS_WAR;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ENTER_LEGION_BOSS_WAR";
	}

	@Override
	public void execute() {
	}
}