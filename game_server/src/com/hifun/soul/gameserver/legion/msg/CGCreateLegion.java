package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求创建军团
 * 
 * @author SevenSoul
 */
@Component
public class CGCreateLegion extends CGMessage{
	
	/** 军团名称 */
	private String legionName;
	
	public CGCreateLegion (){
	}
	
	public CGCreateLegion (
			String legionName ){
			this.legionName = legionName;
	}
	
	@Override
	protected boolean readImpl() {
		legionName = readString();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(legionName);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CREATE_LEGION;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CREATE_LEGION";
	}

	public String getLegionName(){
		return legionName;
	}
		
	public void setLegionName(String legionName){
		this.legionName = legionName;
	}

	@Override
	public void execute() {
	}
}