package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 获取所有奖品
 * 
 * @author SevenSoul
 */
@Component
public class CGGetAllBattleReward extends CGMessage{
	
	
	public CGGetAllBattleReward (){
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
		return MessageType.CG_GET_ALL_BATTLE_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_ALL_BATTLE_REWARD";
	}

	@Override
	public void execute() {
	}
}