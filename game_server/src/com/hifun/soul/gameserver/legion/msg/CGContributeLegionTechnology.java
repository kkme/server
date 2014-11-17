package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求捐献军团科技
 * 
 * @author SevenSoul
 */
@Component
public class CGContributeLegionTechnology extends CGMessage{
	
	/** 科技类型 */
	private int technologyType;
	
	public CGContributeLegionTechnology (){
	}
	
	public CGContributeLegionTechnology (
			int technologyType ){
			this.technologyType = technologyType;
	}
	
	@Override
	protected boolean readImpl() {
		technologyType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(technologyType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_CONTRIBUTE_LEGION_TECHNOLOGY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_CONTRIBUTE_LEGION_TECHNOLOGY";
	}

	public int getTechnologyType(){
		return technologyType;
	}
		
	public void setTechnologyType(int technologyType){
		this.technologyType = technologyType;
	}

	@Override
	public void execute() {
	}
}