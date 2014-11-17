package com.hifun.soul.gameserver.arena.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 查看竞技场排名奖励信息
 * 
 * @author SevenSoul
 */
@Component
public class CGViewArenaRankRewardInfo extends CGMessage{
	
	
	public CGViewArenaRankRewardInfo (){
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
		return MessageType.CG_VIEW_ARENA_RANK_REWARD_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_VIEW_ARENA_RANK_REWARD_INFO";
	}

	@Override
	public void execute() {
	}
}