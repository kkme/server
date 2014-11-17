package com.hifun.soul.gameserver.arena.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 添加竞技场战斗次数
 *
 * @author SevenSoul
 */
@Component
public class GCAddArenaBattleTime extends GCMessage{
	
	/** 剩余挑战次数 */
	private int times;
	/** 增加挑战次数的花费 */
	private int crystal;

	public GCAddArenaBattleTime (){
	}
	
	public GCAddArenaBattleTime (
			int times,
			int crystal ){
			this.times = times;
			this.crystal = crystal;
	}

	@Override
	protected boolean readImpl() {
		times = readInteger();
		crystal = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(times);
		writeInteger(crystal);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ADD_ARENA_BATTLE_TIME;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ADD_ARENA_BATTLE_TIME";
	}

	public int getTimes(){
		return times;
	}
		
	public void setTimes(int times){
		this.times = times;
	}

	public int getCrystal(){
		return crystal;
	}
		
	public void setCrystal(int crystal){
		this.crystal = crystal;
	}
}