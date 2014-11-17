package com.hifun.soul.gameserver.target.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应领取个人目标奖励
 *
 * @author SevenSoul
 */
@Component
public class GCGetTargetReward extends GCMessage{
	
	/** 目标ID */
	private int targetId;
	/** 领奖状态 */
	private int rewardState;
	/** 是否完成全部目标 */
	private boolean isCompletedAll;

	public GCGetTargetReward (){
	}
	
	public GCGetTargetReward (
			int targetId,
			int rewardState,
			boolean isCompletedAll ){
			this.targetId = targetId;
			this.rewardState = rewardState;
			this.isCompletedAll = isCompletedAll;
	}

	@Override
	protected boolean readImpl() {
		targetId = readInteger();
		rewardState = readInteger();
		isCompletedAll = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(targetId);
		writeInteger(rewardState);
		writeBoolean(isCompletedAll);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_TARGET_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_TARGET_REWARD";
	}

	public int getTargetId(){
		return targetId;
	}
		
	public void setTargetId(int targetId){
		this.targetId = targetId;
	}

	public int getRewardState(){
		return rewardState;
	}
		
	public void setRewardState(int rewardState){
		this.rewardState = rewardState;
	}

	public boolean getIsCompletedAll(){
		return isCompletedAll;
	}
		
	public void setIsCompletedAll(boolean isCompletedAll){
		this.isCompletedAll = isCompletedAll;
	}
}