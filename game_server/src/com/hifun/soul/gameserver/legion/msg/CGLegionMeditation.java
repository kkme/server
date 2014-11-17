package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求军团冥想
 * 
 * @author SevenSoul
 */
@Component
public class CGLegionMeditation extends CGMessage{
	
	/** 冥想类型 */
	private int meditationType;
	
	public CGLegionMeditation (){
	}
	
	public CGLegionMeditation (
			int meditationType ){
			this.meditationType = meditationType;
	}
	
	@Override
	protected boolean readImpl() {
		meditationType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(meditationType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_LEGION_MEDITATION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_LEGION_MEDITATION";
	}

	public int getMeditationType(){
		return meditationType;
	}
		
	public void setMeditationType(int meditationType){
		this.meditationType = meditationType;
	}

	@Override
	public void execute() {
	}
}