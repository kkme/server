package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 角色升级事件
 *
 * @author SevenSoul
 */
@Component
public class GCHumanLevelUp extends GCMessage{
	
	/** 当前等级 */
	private int currentLevel;

	public GCHumanLevelUp (){
	}
	
	public GCHumanLevelUp (
			int currentLevel ){
			this.currentLevel = currentLevel;
	}

	@Override
	protected boolean readImpl() {
		currentLevel = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(currentLevel);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_LEVEL_UP;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_LEVEL_UP";
	}

	public int getCurrentLevel(){
		return currentLevel;
	}
		
	public void setCurrentLevel(int currentLevel){
		this.currentLevel = currentLevel;
	}
}