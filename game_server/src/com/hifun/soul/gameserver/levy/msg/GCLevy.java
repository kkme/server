package com.hifun.soul.gameserver.levy.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回征收结果
 *
 * @author SevenSoul
 */
@Component
public class GCLevy extends GCMessage{
	
	/** 剩余征收次数 */
	private int remainLevyNum;
	/** 当前税收收益 */
	private int levyRevenue;

	public GCLevy (){
	}
	
	public GCLevy (
			int remainLevyNum,
			int levyRevenue ){
			this.remainLevyNum = remainLevyNum;
			this.levyRevenue = levyRevenue;
	}

	@Override
	protected boolean readImpl() {
		remainLevyNum = readInteger();
		levyRevenue = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(remainLevyNum);
		writeInteger(levyRevenue);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LEVY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LEVY";
	}

	public int getRemainLevyNum(){
		return remainLevyNum;
	}
		
	public void setRemainLevyNum(int remainLevyNum){
		this.remainLevyNum = remainLevyNum;
	}

	public int getLevyRevenue(){
		return levyRevenue;
	}
		
	public void setLevyRevenue(int levyRevenue){
		this.levyRevenue = levyRevenue;
	}
}