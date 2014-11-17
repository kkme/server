package com.hifun.soul.gameserver.faction.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求选择阵营
 * 
 * @author SevenSoul
 */
@Component
public class CGSelectFaction extends CGMessage{
	
	/** 选择的阵营类型 */
	private int factionType;
	
	public CGSelectFaction (){
	}
	
	public CGSelectFaction (
			int factionType ){
			this.factionType = factionType;
	}
	
	@Override
	protected boolean readImpl() {
		factionType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(factionType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SELECT_FACTION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SELECT_FACTION";
	}

	public int getFactionType(){
		return factionType;
	}
		
	public void setFactionType(int factionType){
		this.factionType = factionType;
	}

	@Override
	public void execute() {
	}
}