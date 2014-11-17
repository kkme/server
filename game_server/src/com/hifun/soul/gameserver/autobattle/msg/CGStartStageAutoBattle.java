package com.hifun.soul.gameserver.autobattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 关卡开始扫荡
 * 
 * @author SevenSoul
 */
@Component
public class CGStartStageAutoBattle extends CGMessage{
	
	/** 关卡id */
	private int stageId;
	/** 次数 */
	private int times;
	
	public CGStartStageAutoBattle (){
	}
	
	public CGStartStageAutoBattle (
			int stageId,
			int times ){
			this.stageId = stageId;
			this.times = times;
	}
	
	@Override
	protected boolean readImpl() {
		stageId = readInteger();
		times = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(stageId);
		writeInteger(times);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_START_STAGE_AUTO_BATTLE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_START_STAGE_AUTO_BATTLE";
	}

	public int getStageId(){
		return stageId;
	}
		
	public void setStageId(int stageId){
		this.stageId = stageId;
	}

	public int getTimes(){
		return times;
	}
		
	public void setTimes(int times){
		this.times = times;
	}

	@Override
	public void execute() {
	}
}