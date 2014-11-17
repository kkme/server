package com.hifun.soul.gameserver.legionboss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 充能
 *
 * @author SevenSoul
 */
@Component
public class GCChargedLegionBossWar extends GCMessage{
	
	/** 充能百分比 */
	private int chargedstrikeRate;
	/** 是否已经充能满 */
	private boolean isFull;

	public GCChargedLegionBossWar (){
	}
	
	public GCChargedLegionBossWar (
			int chargedstrikeRate,
			boolean isFull ){
			this.chargedstrikeRate = chargedstrikeRate;
			this.isFull = isFull;
	}

	@Override
	protected boolean readImpl() {
		chargedstrikeRate = readInteger();
		isFull = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(chargedstrikeRate);
		writeBoolean(isFull);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHARGED_LEGION_BOSS_WAR;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHARGED_LEGION_BOSS_WAR";
	}

	public int getChargedstrikeRate(){
		return chargedstrikeRate;
	}
		
	public void setChargedstrikeRate(int chargedstrikeRate){
		this.chargedstrikeRate = chargedstrikeRate;
	}

	public boolean getIsFull(){
		return isFull;
	}
		
	public void setIsFull(boolean isFull){
		this.isFull = isFull;
	}
}