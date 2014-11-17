package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 请求清除所有矿坑cd需要的花费
 *
 * @author SevenSoul
 */
@Component
public class GCRemoveAllMineCd extends GCMessage{
	
	/** 消耗货币类型 */
	private int costType;
	/** 消耗货币数量 */
	private int costNum;

	public GCRemoveAllMineCd (){
	}
	
	public GCRemoveAllMineCd (
			int costType,
			int costNum ){
			this.costType = costType;
			this.costNum = costNum;
	}

	@Override
	protected boolean readImpl() {
		costType = readInteger();
		costNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(costType);
		writeInteger(costNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_REMOVE_ALL_MINE_CD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_REMOVE_ALL_MINE_CD";
	}

	public int getCostType(){
		return costType;
	}
		
	public void setCostType(int costType){
		this.costType = costType;
	}

	public int getCostNum(){
		return costNum;
	}
		
	public void setCostNum(int costNum){
		this.costNum = costNum;
	}
}