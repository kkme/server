package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求获得军团任务排行奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetLegionTaskRankReward extends CGMessage{
	
	
	public CGGetLegionTaskRankReward (){
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
		return MessageType.CG_GET_LEGION_TASK_RANK_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_LEGION_TASK_RANK_REWARD";
	}

	@Override
	public void execute() {
	}
}