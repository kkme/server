package com.hifun.soul.gameserver.boss.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 充能
 *
 * @author SevenSoul
 */
@Component
public class GCChargedBossWar extends GCMessage{
	
	/** 充能百分比 */
	private int chargedstrikeRate;

	public GCChargedBossWar (){
	}
	
	public GCChargedBossWar (
			int chargedstrikeRate ){
			this.chargedstrikeRate = chargedstrikeRate;
	}

	@Override
	protected boolean readImpl() {
		chargedstrikeRate = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(chargedstrikeRate);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHARGED_BOSS_WAR;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHARGED_BOSS_WAR";
	}

	public int getChargedstrikeRate(){
		return chargedstrikeRate;
	}
		
	public void setChargedstrikeRate(int chargedstrikeRate){
		this.chargedstrikeRate = chargedstrikeRate;
	}
}