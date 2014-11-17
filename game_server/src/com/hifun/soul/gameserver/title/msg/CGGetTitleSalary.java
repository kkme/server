package com.hifun.soul.gameserver.title.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求领取当日军衔俸禄
 * 
 * @author SevenSoul
 */
@Component
public class CGGetTitleSalary extends CGMessage{
	
	
	public CGGetTitleSalary (){
	}
	
	
	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_TITLE_SALARY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_TITLE_SALARY";
	}

	@Override
	public void execute() {
	}
}