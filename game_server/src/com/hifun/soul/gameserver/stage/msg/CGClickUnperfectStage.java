package com.hifun.soul.gameserver.stage.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求点击不完美关卡
 * 
 * @author SevenSoul
 */
@Component
public class CGClickUnperfectStage extends CGMessage{
	
	/** 关卡id */
	private int stageId;
	
	public CGClickUnperfectStage (){
	}
	
	public CGClickUnperfectStage (
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
		return MessageType.CG_CLICK_UNPERFECT_STAGE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CLICK_UNPERFECT_STAGE";
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