package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 星运占星
 * 
 * @author SevenSoul
 */
@Component
public class CGHoroscopeGamble extends CGMessage{
	
	/** 占星类型 */
	private int stargazerId;
	
	public CGHoroscopeGamble (){
	}
	
	public CGHoroscopeGamble (
			int stargazerId ){
			this.stargazerId = stargazerId;
	}
	
	@Override
	protected boolean readImpl() {
		stargazerId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(stargazerId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_HOROSCOPE_GAMBLE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HOROSCOPE_GAMBLE";
	}

	public int getStargazerId(){
		return stargazerId;
	}
		
	public void setStargazerId(int stargazerId){
		this.stargazerId = stargazerId;
	}

	@Override
	public void execute() {
	}
}