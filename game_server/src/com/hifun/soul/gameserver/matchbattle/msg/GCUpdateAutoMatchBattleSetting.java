package com.hifun.soul.gameserver.matchbattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新自动参战设置
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateAutoMatchBattleSetting extends GCMessage{
	
	/** 是否自动参战 */
	private boolean isAutoJoinBattle;

	public GCUpdateAutoMatchBattleSetting (){
	}
	
	public GCUpdateAutoMatchBattleSetting (
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
		return MessageType.GC_UPDATE_AUTO_MATCH_BATTLE_SETTING;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_AUTO_MATCH_BATTLE_SETTING";
	}

	public boolean getIsAutoJoinBattle(){
		return isAutoJoinBattle;
	}
		
	public void setIsAutoJoinBattle(boolean isAutoJoinBattle){
		this.isAutoJoinBattle = isAutoJoinBattle;
	}
}