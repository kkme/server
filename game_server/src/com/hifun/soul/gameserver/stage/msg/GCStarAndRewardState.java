package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 关卡星星总数变化时同步星星总数量和奖励可领取状态
 *
 * @author SevenSoul
 */
@Component
public class GCStarAndRewardState extends GCMessage{
	
	/** 可领取的下一级评星奖励星星数 */
	private int totalStar;
	/** 是否可以领取下一级评星奖励 */
	private boolean canGet;

	public GCStarAndRewardState (){
	}
	
	public GCStarAndRewardState (
			int totalStar,
			boolean canGet ){
			this.totalStar = totalStar;
			this.canGet = canGet;
	}

	@Override
	protected boolean readImpl() {
		totalStar = readInteger();
		canGet = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(totalStar);
		writeBoolean(canGet);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STAR_AND_REWARD_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STAR_AND_REWARD_STATE";
	}

	public int getTotalStar(){
		return totalStar;
	}
		
	public void setTotalStar(int totalStar){
		this.totalStar = totalStar;
	}

	public boolean getCanGet(){
		return canGet;
	}
		
	public void setCanGet(boolean canGet){
		this.canGet = canGet;
	}
}