package com.hifun.soul.gameserver.title.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应领取当日军衔俸禄
 *
 * @author SevenSoul
 */
@Component
public class GCGetTitleSalary extends GCMessage{
	
	/** 当日俸禄是否领取 */
	private boolean isGotTitleSalary;

	public GCGetTitleSalary (){
	}
	
	public GCGetTitleSalary (
			boolean isGotTitleSalary ){
			this.isGotTitleSalary = isGotTitleSalary;
	}

	@Override
	protected boolean readImpl() {
		isGotTitleSalary = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeBoolean(isGotTitleSalary);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_TITLE_SALARY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_TITLE_SALARY";
	}

	public boolean getIsGotTitleSalary(){
		return isGotTitleSalary;
	}
		
	public void setIsGotTitleSalary(boolean isGotTitleSalary){
		this.isGotTitleSalary = isGotTitleSalary;
	}
}