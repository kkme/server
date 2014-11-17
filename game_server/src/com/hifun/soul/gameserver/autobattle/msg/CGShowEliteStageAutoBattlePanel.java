package com.hifun.soul.gameserver.autobattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开精英副本扫荡面板
 * 
 * @author SevenSoul
 */
@Component
public class CGShowEliteStageAutoBattlePanel extends CGMessage{
	
	/** 精英副本类型 */
	private int eliteStageType;
	
	public CGShowEliteStageAutoBattlePanel (){
	}
	
	public CGShowEliteStageAutoBattlePanel (
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
		return MessageType.CG_SHOW_ELITE_STAGE_AUTO_BATTLE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_ELITE_STAGE_AUTO_BATTLE_PANEL";
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