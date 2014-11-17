package com.hifun.soul.gameserver.technology.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求升级科技
 * 
 * @author SevenSoul
 */
@Component
public class CGUpgradeTechnology extends CGMessage{
	
	/** 科技id */
	private int technologyId;
	
	public CGUpgradeTechnology (){
	}
	
	public CGUpgradeTechnology (
			int technologyId ){
			this.technologyId = technologyId;
	}
	
	@Override
	protected boolean readImpl() {
		technologyId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(technologyId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_UPGRADE_TECHNOLOGY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_UPGRADE_TECHNOLOGY";
	}

	public int getTechnologyId(){
		return technologyId;
	}
		
	public void setTechnologyId(int technologyId){
		this.technologyId = technologyId;
	}

	@Override
	public void execute() {
	}
}