package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 重置矿坑结果
 *
 * @author SevenSoul
 */
@Component
public class GCResetMineField extends GCMessage{
	
	/** 矿坑位置索引 */
	private int index;
	/** 重置矿坑消耗货币类型 */
	private int resetMineCostType;
	/** 重置矿坑消耗货币数量 */
	private int resetMineCostNum;

	public GCResetMineField (){
	}
	
	public GCResetMineField (
			int index,
			int resetMineCostType,
			int resetMineCostNum ){
			this.index = index;
			this.resetMineCostType = resetMineCostType;
			this.resetMineCostNum = resetMineCostNum;
	}

	@Override
	protected boolean readImpl() {
		index = readInteger();
		resetMineCostType = readInteger();
		resetMineCostNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(index);
		writeInteger(resetMineCostType);
		writeInteger(resetMineCostNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_RESET_MINE_FIELD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_RESET_MINE_FIELD";
	}

	public int getIndex(){
		return index;
	}
		
	public void setIndex(int index){
		this.index = index;
	}

	public int getResetMineCostType(){
		return resetMineCostType;
	}
		
	public void setResetMineCostType(int resetMineCostType){
		this.resetMineCostType = resetMineCostType;
	}

	public int getResetMineCostNum(){
		return resetMineCostNum;
	}
		
	public void setResetMineCostNum(int resetMineCostNum){
		this.resetMineCostNum = resetMineCostNum;
	}
}