package com.hifun.soul.gameserver.matchbattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 更新自动参战设置
 * 
 * @author SevenSoul
 */
@Component
public class CGUpdateAutoMatchBattleSetting extends CGMessage{
	
	/** 是否自动参战 */
	private boolean isAutoJoinBattle;
	
	public CGUpdateAutoMatchBattleSetting (){
	}
	
	public CGUpdateAutoMatchBattleSetting (
			boolean isAutoJoinBattle ){
			this.isAutoJoinBattle = isAutoJoinBattle;
	}
	
	@Override
	protected boolean readImpl() {
		isAutoJoinBattle = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(isAutoJoinBattle);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_UPDATE_AUTO_MATCH_BATTLE_SETTING;
	}
	
	@Override
	public String getTypeName() {
		return "CG_UPDATE_AUTO_MATCH_BATTLE_SETTING";
	}

	public boolean getIsAutoJoinBattle(){
		return isAutoJoinBattle;
	}
		
	public void setIsAutoJoinBattle(boolean isAutoJoinBattle){
		this.isAutoJoinBattle = isAutoJoinBattle;
	}

	@Override
	public void execute() {
	}
}