package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 矿坑收获请求
 * 
 * @author SevenSoul
 */
@Component
public class CGMineHarvest extends CGMessage{
	
	/** 矿坑位置索引 */
	private int index;
	
	public CGMineHarvest (){
	}
	
	public CGMineHarvest (
			int index ){
			this.index = index;
	}
	
	@Override
	protected boolean readImpl() {
		index = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(index);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_MINE_HARVEST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_MINE_HARVEST";
	}

	public int getIndex(){
		return index;
	}
		
	public void setIndex(int index){
		this.index = index;
	}

	@Override
	public void execute() {
	}
}