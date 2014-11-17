package com.hifun.soul.gameserver.legionmine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求鼓舞
 * 
 * @author SevenSoul
 */
@Component
public class CGLegionMineWarEncourage extends CGMessage{
	
	/** 鼓舞类型(冥想力还是魔晶) */
	private int encourageType;
	
	public CGLegionMineWarEncourage (){
	}
	
	public CGLegionMineWarEncourage (
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
		return MessageType.CG_LEGION_MINE_WAR_ENCOURAGE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LEGION_MINE_WAR_ENCOURAGE";
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