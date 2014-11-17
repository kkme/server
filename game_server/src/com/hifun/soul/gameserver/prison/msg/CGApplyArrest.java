package com.hifun.soul.gameserver.prison.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 申请抓捕
 * 
 * @author SevenSoul
 */
@Component
public class CGApplyArrest extends CGMessage{
	
	/** 要逮捕的角色ID */
	private long arrestHumanId;
	
	public CGApplyArrest (){
	}
	
	public CGApplyArrest (
			long arrestHumanId ){
			this.arrestHumanId = arrestHumanId;
	}
	
	@Override
	protected boolean readImpl() {
		arrestHumanId = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeLong(arrestHumanId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_APPLY_ARREST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_APPLY_ARREST";
	}

	public long getArrestHumanId(){
		return arrestHumanId;
	}
		
	public void setArrestHumanId(long arrestHumanId){
		this.arrestHumanId = arrestHumanId;
	}

	@Override
	public void execute() {
	}
}