package com.hifun.soul.gameserver.elitestage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 精英副本进入战斗
 * 
 * @author SevenSoul
 */
@Component
public class CGAttackEliteStageBattle extends CGMessage{
	
	/** 精英副本id */
	private int stageId;
	/** 是否忽略警示信息 */
	private boolean ignoreWarning;
	
	public CGAttackEliteStageBattle (){
	}
	
	public CGAttackEliteStageBattle (
			int stageId,
			boolean ignoreWarning ){
			this.stageId = stageId;
			this.ignoreWarning = ignoreWarning;
	}
	
	@Override
	protected boolean readImpl() {
		stageId = readInteger();
		ignoreWarning = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(stageId);
		writeBoolean(ignoreWarning);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ATTACK_ELITE_STAGE_BATTLE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ATTACK_ELITE_STAGE_BATTLE";
	}

	public int getStageId(){
		return stageId;
	}
		
	public void setStageId(int stageId){
		this.stageId = stageId;
	}

	public boolean getIgnoreWarning(){
		return ignoreWarning;
	}
		
	public void setIgnoreWarning(boolean ignoreWarning){
		this.ignoreWarning = ignoreWarning;
	}

	@Override
	public void execute() {
	}
}