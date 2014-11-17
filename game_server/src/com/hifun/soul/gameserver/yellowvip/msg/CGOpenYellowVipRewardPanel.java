package com.hifun.soul.gameserver.yellowvip.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 更新黄钻新手礼包领取状态
 * 
 * @author SevenSoul
 */
@Component
public class CGOpenYellowVipRewardPanel extends CGMessage{
	
	
	public CGOpenYellowVipRewardPanel (){
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
		return MessageType.CG_OPEN_YELLOW_VIP_REWARD_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_OPEN_YELLOW_VIP_REWARD_PANEL";
	}

	@Override
	public void execute() {
	}
}