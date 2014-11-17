package com.hifun.soul.gameserver.technology.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求查看科技信息
 * 
 * @author SevenSoul
 */
@Component
public class CGShowTechnologyInfo extends CGMessage{
	
	/** 科技id */
	private int technologyId;
	
	public CGShowTechnologyInfo (){
	}
	
	public CGShowTechnologyInfo (
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
		return MessageType.CG_SHOW_TECHNOLOGY_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_TECHNOLOGY_INFO";
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