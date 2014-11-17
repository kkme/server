package com.hifun.soul.gameserver.horoscope.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 拾取主背包星运
 * 
 * @author SevenSoul
 */
@Component
public class CGHoroscopePickUp extends CGMessage{
	
	/** from背包位置 */
	private int fromBagIndex;
	
	public CGHoroscopePickUp (){
	}
	
	public CGHoroscopePickUp (
			int fromBagIndex ){
			this.fromBagIndex = fromBagIndex;
	}
	
	@Override
	protected boolean readImpl() {
		fromBagIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(fromBagIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_HOROSCOPE_PICK_UP;
	}
	
	@Override
	public String getTypeName() {
		return "CG_HOROSCOPE_PICK_UP";
	}

	public int getFromBagIndex(){
		return fromBagIndex;
	}
		
	public void setFromBagIndex(int fromBagIndex){
		this.fromBagIndex = fromBagIndex;
	}

	@Override
	public void execute() {
	}
}