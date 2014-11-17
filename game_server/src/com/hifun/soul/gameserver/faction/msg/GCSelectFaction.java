package com.hifun.soul.gameserver.faction.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应选择阵营结果
 *
 * @author SevenSoul
 */
@Component
public class GCSelectFaction extends GCMessage{
	
	/** 选择的阵营类型 */
	private int factionType;

	public GCSelectFaction (){
	}
	
	public GCSelectFaction (
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
		return MessageType.GC_SELECT_FACTION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SELECT_FACTION";
	}

	public int getFactionType(){
		return factionType;
	}
		
	public void setFactionType(int factionType){
		this.factionType = factionType;
	}
}