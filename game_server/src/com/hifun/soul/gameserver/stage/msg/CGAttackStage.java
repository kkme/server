package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 攻打关卡
 * 
 * @author SevenSoul
 */
@Component
public class CGAttackStage extends CGMessage{
	
	/** 关卡id */
	private int stageId;
	
	public CGAttackStage (){
	}
	
	public CGAttackStage (
			int stageId ){
			this.stageId = stageId;
	}
	
	@Override
	protected boolean readImpl() {
		stageId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(stageId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ATTACK_STAGE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ATTACK_STAGE";
	}

	public int getStageId(){
		return stageId;
	}
		
	public void setStageId(int stageId){
		this.stageId = stageId;
	}

	@Override
	public void execute() {
	}
}