package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应获得军团任务排行奖励 
 *
 * @author SevenSoul
 */
@Component
public class GCGetLegionTaskRankReward extends GCMessage{
	
	/** 是否有排名奖励 */
	private boolean hasRankReward;

	public GCGetLegionTaskRankReward (){
	}
	
	public GCGetLegionTaskRankReward (
			boolean hasRankReward ){
			this.hasRankReward = hasRankReward;
	}

	@Override
	protected boolean readImpl() {
		hasRankReward = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(hasRankReward);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_LEGION_TASK_RANK_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_LEGION_TASK_RANK_REWARD";
	}

	public boolean getHasRankReward(){
		return hasRankReward;
	}
		
	public void setHasRankReward(boolean hasRankReward){
		this.hasRankReward = hasRankReward;
	}
}