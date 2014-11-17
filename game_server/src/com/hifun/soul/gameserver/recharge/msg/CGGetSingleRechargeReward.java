package com.hifun.soul.gameserver.recharge.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求领取单笔充值奖励
 * 
 * @author SevenSoul
 */
@Component
public class CGGetSingleRechargeReward extends CGMessage{
	
	/** 档位ID */
	private int gradeId;
	
	public CGGetSingleRechargeReward (){
	}
	
	public CGGetSingleRechargeReward (
			int gradeId ){
			this.gradeId = gradeId;
	}
	
	@Override
	protected boolean readImpl() {
		gradeId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(gradeId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_GET_SINGLE_RECHARGE_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "CG_GET_SINGLE_RECHARGE_REWARD";
	}

	public int getGradeId(){
		return gradeId;
	}
		
	public void setGradeId(int gradeId){
		this.gradeId = gradeId;
	}

	@Override
	public void execute() {
	}
}