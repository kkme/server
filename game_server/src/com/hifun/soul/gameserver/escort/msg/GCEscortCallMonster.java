package com.hifun.soul.gameserver.escort.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应召唤最高品质怪物
 *
 * @author SevenSoul
 */
@Component
public class GCEscortCallMonster extends GCMessage{
	
	/** 押运怪物类型 */
	private int defaultMonsterType;

	public GCEscortCallMonster (){
	}
	
	public GCEscortCallMonster (
			int defaultMonsterType ){
			this.defaultMonsterType = defaultMonsterType;
	}

	@Override
	protected boolean readImpl() {
		defaultMonsterType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(defaultMonsterType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ESCORT_CALL_MONSTER;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ESCORT_CALL_MONSTER";
	}

	public int getDefaultMonsterType(){
		return defaultMonsterType;
	}
		
	public void setDefaultMonsterType(int defaultMonsterType){
		this.defaultMonsterType = defaultMonsterType;
	}
}