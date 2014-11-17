package com.hifun.soul.gameserver.elitestage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 刷新精英副本的挑战状态
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateEliteStageChallengeState extends GCMessage{
	
	/** 刷新的精英副本id列表 */
	private int[] changedStageIds;

	public GCUpdateEliteStageChallengeState (){
	}
	
	public GCUpdateEliteStageChallengeState (
			int[] changedStageIds ){
			this.changedStageIds = changedStageIds;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		changedStageIds = new int[count];
		for(int i=0; i<count; i++){
			changedStageIds[i] = readInteger();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(changedStageIds.length);
	for(int i=0; i<changedStageIds.length; i++){
	Integer objchangedStageIds = changedStageIds[i];
			writeInteger(objchangedStageIds);
}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_ELITE_STAGE_CHALLENGE_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_ELITE_STAGE_CHALLENGE_STATE";
	}

	public int[] getChangedStageIds(){
		return changedStageIds;
	}

	public void setChangedStageIds(int[] changedStageIds){
		this.changedStageIds = changedStageIds;
	}	
}