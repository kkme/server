package com.hifun.soul.gameserver.legionboss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 是否有军团boss战奖励
 *
 * @author SevenSoul
 */
@Component
public class GCHasLegionBossReward extends GCMessage{
	

	public GCHasLegionBossReward (){
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
		return MessageType.GC_HAS_LEGION_BOSS_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HAS_LEGION_BOSS_REWARD";
	}
}