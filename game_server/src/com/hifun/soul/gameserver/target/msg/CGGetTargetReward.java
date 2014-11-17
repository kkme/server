package com.hifun.soul.gameserver.target.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求领取个人目标奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetTargetReward extends CGMessage{
	
	/** 目标ID */
	private int targetId;
	
	public CGGetTargetReward (){
	}
	
	public CGGetTargetReward (
			int targetId ){
			this.targetId = targetId;
	}
	
	@Override
	protected boolean readImpl() {
		targetId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(targetId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_TARGET_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_TARGET_REWARD";
	}

	public int getTargetId(){
		return targetId;
	}
		
	public void setTargetId(int targetId){
		this.targetId = targetId;
	}

	@Override
	public void execute() {
	}
}