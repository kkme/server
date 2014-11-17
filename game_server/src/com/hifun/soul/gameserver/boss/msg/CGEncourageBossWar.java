package com.hifun.soul.gameserver.boss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 鼓舞
 * 
 * @author SevenSoul
 */
@Component
public class CGEncourageBossWar extends CGMessage{
	
	/** 鼓舞类型(金币还是魔晶) */
	private int encourageType;
	
	public CGEncourageBossWar (){
	}
	
	public CGEncourageBossWar (
			int encourageType ){
			this.encourageType = encourageType;
	}
	
	@Override
	protected boolean readImpl() {
		encourageType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(encourageType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ENCOURAGE_BOSS_WAR;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ENCOURAGE_BOSS_WAR";
	}

	public int getEncourageType(){
		return encourageType;
	}
		
	public void setEncourageType(int encourageType){
		this.encourageType = encourageType;
	}

	@Override
	public void execute() {
	}
}