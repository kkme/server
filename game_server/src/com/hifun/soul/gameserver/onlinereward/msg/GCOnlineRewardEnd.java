package com.hifun.soul.gameserver.onlinereward.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 在线奖励领取完
 *
 * @author SevenSoul
 */
@Component
public class GCOnlineRewardEnd extends GCMessage{
	

	public GCOnlineRewardEnd (){
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
		return MessageType.GC_ONLINE_REWARD_END;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ONLINE_REWARD_END";
	}
}