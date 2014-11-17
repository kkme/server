package com.hifun.soul.gameserver.autobattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 精英副本开始扫荡
 * 
 * @author SevenSoul
 */
@Component
public class CGStartEliteAutoBattle extends CGMessage{
	
	/** 精英副本类型 */
	private int eliteStageType;
	
	public CGStartEliteAutoBattle (){
	}
	
	public CGStartEliteAutoBattle (
			int eliteStageType ){
			this.eliteStageType = eliteStageType;
	}
	
	@Override
	protected boolean readImpl() {
		eliteStageType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(eliteStageType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_START_ELITE_AUTO_BATTLE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_START_ELITE_AUTO_BATTLE";
	}

	public int getEliteStageType(){
		return eliteStageType;
	}
		
	public void setEliteStageType(int eliteStageType){
		this.eliteStageType = eliteStageType;
	}

	@Override
	public void execute() {
	}
}