package com.hifun.soul.gameserver.legionboss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 鼓舞
 *
 * @author SevenSoul
 */
@Component
public class GCEncourageLegionBossWar extends GCMessage{
	
	/** 鼓舞百分比 */
	private int encourageRate;
	/** 是否已经鼓舞满 */
	private boolean isFull;

	public GCEncourageLegionBossWar (){
	}
	
	public GCEncourageLegionBossWar (
			int encourageRate,
			boolean isFull ){
			this.encourageRate = encourageRate;
			this.isFull = isFull;
	}

	@Override
	protected boolean readImpl() {
		encourageRate = readInteger();
		isFull = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(encourageRate);
		writeBoolean(isFull);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ENCOURAGE_LEGION_BOSS_WAR;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ENCOURAGE_LEGION_BOSS_WAR";
	}

	public int getEncourageRate(){
		return encourageRate;
	}
		
	public void setEncourageRate(int encourageRate){
		this.encourageRate = encourageRate;
	}

	public boolean getIsFull(){
		return isFull;
	}
		
	public void setIsFull(boolean isFull){
		this.isFull = isFull;
	}
}