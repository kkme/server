package com.hifun.soul.gameserver.legionmine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应鼓舞
 *
 * @author SevenSoul
 */
@Component
public class GCLegionMineWarEncourage extends GCMessage{
	
	/** 鼓舞百分比 */
	private int encourageRate;
	/** 是否已经鼓舞满 */
	private boolean isFull;
	/** 攻击加成百分比 */
	private int attackRate;

	public GCLegionMineWarEncourage (){
	}
	
	public GCLegionMineWarEncourage (
			int encourageRate,
			boolean isFull,
			int attackRate ){
			this.encourageRate = encourageRate;
			this.isFull = isFull;
			this.attackRate = attackRate;
	}

	@Override
	protected boolean readImpl() {
		encourageRate = readInteger();
		isFull = readBoolean();
		attackRate = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(encourageRate);
		writeBoolean(isFull);
		writeInteger(attackRate);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LEGION_MINE_WAR_ENCOURAGE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LEGION_MINE_WAR_ENCOURAGE";
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

	public int getAttackRate(){
		return attackRate;
	}
		
	public void setAttackRate(int attackRate){
		this.attackRate = attackRate;
	}
}