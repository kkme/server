package com.hifun.soul.gameserver.autobattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 打开关卡扫荡面板
 * 
 * @author SevenSoul
 */
@Component
public class CGShowStageAutoBattlePanel extends CGMessage{
	
	/** 关卡id */
	private int stageId;
	
	public CGShowStageAutoBattlePanel (){
	}
	
	public CGShowStageAutoBattlePanel (
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
		return MessageType.CG_SHOW_STAGE_AUTO_BATTLE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_STAGE_AUTO_BATTLE_PANEL";
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