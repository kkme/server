package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 领取关卡评星奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetStageStarReward extends CGMessage{
	
	
	public CGGetStageStarReward (){
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
		return MessageType.CG_GET_STAGE_STAR_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_STAGE_STAR_REWARD";
	}

	@Override
	public void execute() {
	}
}