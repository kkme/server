package com.hifun.soul.gameserver.elitestage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 刷新精英副本的挑战状态
 * 
 * @author SevenSoul
 */
@Component
public class CGRefreshEliteStageChallengeState extends CGMessage{
	
	/** 精英副本类型id */
	private int stageTypeId;
	
	public CGRefreshEliteStageChallengeState (){
	}
	
	public CGRefreshEliteStageChallengeState (
			int stageTypeId ){
			this.stageTypeId = stageTypeId;
	}
	
	@Override
	protected boolean readImpl() {
		stageTypeId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(stageTypeId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_REFRESH_ELITE_STAGE_CHALLENGE_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_REFRESH_ELITE_STAGE_CHALLENGE_STATE";
	}

	public int getStageTypeId(){
		return stageTypeId;
	}
		
	public void setStageTypeId(int stageTypeId){
		this.stageTypeId = stageTypeId;
	}

	@Override
	public void execute() {
	}
}