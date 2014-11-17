package com.hifun.soul.gameserver.legionmine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求移动
 * 
 * @author SevenSoul
 */
@Component
public class CGLegionMineWarMove extends CGMessage{
	
	/** 目标矿堆索引 */
	private int toIndex;
	
	public CGLegionMineWarMove (){
	}
	
	public CGLegionMineWarMove (
			int toIndex ){
			this.toIndex = toIndex;
	}
	
	@Override
	protected boolean readImpl() {
		toIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(toIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_LEGION_MINE_WAR_MOVE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LEGION_MINE_WAR_MOVE";
	}

	public int getToIndex(){
		return toIndex;
	}
		
	public void setToIndex(int toIndex){
		this.toIndex = toIndex;
	}

	@Override
	public void execute() {
	}
}