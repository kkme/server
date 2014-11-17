package com.hifun.soul.gameserver.boss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 获取boss奖励
 *
 * @author SevenSoul
 */
@Component
public class GCGetBossReward extends GCMessage{
	

	public GCGetBossReward (){
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
		return MessageType.GC_GET_BOSS_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_BOSS_REWARD";
	}
}