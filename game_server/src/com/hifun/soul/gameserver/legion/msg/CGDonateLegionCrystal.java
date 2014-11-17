package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求捐献军团魔晶
 * 
 * @author SevenSoul
 */
@Component
public class CGDonateLegionCrystal extends CGMessage{
	
	/** 捐献魔晶 */
	private int donateCrystal;
	
	public CGDonateLegionCrystal (){
	}
	
	public CGDonateLegionCrystal (
			int donateCrystal ){
			this.donateCrystal = donateCrystal;
	}
	
	@Override
	protected boolean readImpl() {
		donateCrystal = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(donateCrystal);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_DONATE_LEGION_CRYSTAL;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DONATE_LEGION_CRYSTAL";
	}

	public int getDonateCrystal(){
		return donateCrystal;
	}
		
	public void setDonateCrystal(int donateCrystal){
		this.donateCrystal = donateCrystal;
	}

	@Override
	public void execute() {
	}
}