package com.hifun.soul.gameserver.boss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 是否有boss战奖励
 *
 * @author SevenSoul
 */
@Component
public class GCHasBossReward extends GCMessage{
	

	public GCHasBossReward (){
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
		return MessageType.GC_HAS_BOSS_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HAS_BOSS_REWARD";
	}
}