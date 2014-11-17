package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 查看关卡信息
 * 
 * @author SevenSoul
 */
@Component
public class CGStageInfo extends CGMessage{
	
	/** 关卡id */
	private int stageId;
	
	public CGStageInfo (){
	}
	
	public CGStageInfo (
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
		return MessageType.CG_STAGE_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_STAGE_INFO";
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