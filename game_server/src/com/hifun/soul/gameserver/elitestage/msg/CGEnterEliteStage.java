package com.hifun.soul.gameserver.elitestage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开某个类型的精英副本
 * 
 * @author SevenSoul
 */
@Component
public class CGEnterEliteStage extends CGMessage{
	
	/** 精英副本类型id */
	private int stageTypeId;
	
	public CGEnterEliteStage (){
	}
	
	public CGEnterEliteStage (
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
		return MessageType.CG_ENTER_ELITE_STAGE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ENTER_ELITE_STAGE";
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