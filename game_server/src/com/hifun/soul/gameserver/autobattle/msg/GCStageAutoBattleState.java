package com.hifun.soul.gameserver.autobattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 关卡扫荡状态
 *
 * @author SevenSoul
 */
@Component
public class GCStageAutoBattleState extends GCMessage{
	
	/** 扫荡状态 */
	private int state;

	public GCStageAutoBattleState (){
	}
	
	public GCStageAutoBattleState (
			int state ){
			this.state = state;
	}

	@Override
	protected boolean readImpl() {
		state = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(state);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_STAGE_AUTO_BATTLE_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_STAGE_AUTO_BATTLE_STATE";
	}

	public int getState(){
		return state;
	}
		
	public void setState(int state){
		this.state = state;
	}
}