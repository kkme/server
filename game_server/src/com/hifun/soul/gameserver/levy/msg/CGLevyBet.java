package com.hifun.soul.gameserver.levy.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求押注
 * 
 * @author SevenSoul
 */
@Component
public class CGLevyBet extends CGMessage{
	
	/** 押注类型 */
	private int betType;
	
	public CGLevyBet (){
	}
	
	public CGLevyBet (
			int betType ){
			this.betType = betType;
	}
	
	@Override
	protected boolean readImpl() {
		betType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(betType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_LEVY_BET;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LEVY_BET";
	}

	public int getBetType(){
		return betType;
	}
		
	public void setBetType(int betType){
		this.betType = betType;
	}

	@Override
	public void execute() {
	}
}