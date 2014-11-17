package com.hifun.soul.gameserver.boss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 获取boss奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetBossReward extends CGMessage{
	
	
	public CGGetBossReward (){
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
		return MessageType.CG_GET_BOSS_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_BOSS_REWARD";
	}

	@Override
	public void execute() {
	}
}