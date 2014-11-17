package com.hifun.soul.gameserver.recharge.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应领取首充奖励
 *
 * @author SevenSoul
 */
@Component
public class GCGetFirstRechargeReward extends GCMessage{
	

	public GCGetFirstRechargeReward (){
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
		return MessageType.GC_GET_FIRST_RECHARGE_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_FIRST_RECHARGE_REWARD";
	}
}