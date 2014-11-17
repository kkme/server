package com.hifun.soul.gameserver.arena.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 领取竞技场排名奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetArenaRankReward extends CGMessage{
	
	
	public CGGetArenaRankReward (){
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
		return MessageType.CG_GET_ARENA_RANK_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_ARENA_RANK_REWARD";
	}

	@Override
	public void execute() {
	}
}