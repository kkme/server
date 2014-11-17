package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求兑换军团头衔
 * 
 * @author SevenSoul
 */
@Component
public class CGExchangeLegionTitle extends CGMessage{
	
	/** 头衔ID */
	private int titleId;
	
	public CGExchangeLegionTitle (){
	}
	
	public CGExchangeLegionTitle (
			int titleId ){
			this.titleId = titleId;
	}
	
	@Override
	protected boolean readImpl() {
		titleId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(titleId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_EXCHANGE_LEGION_TITLE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_EXCHANGE_LEGION_TITLE";
	}

	public int getTitleId(){
		return titleId;
	}
		
	public void setTitleId(int titleId){
		this.titleId = titleId;
	}

	@Override
	public void execute() {
	}
}