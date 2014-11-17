package com.hifun.soul.gameserver.activity.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 参加活动
 * 
 * @author SevenSoul
 */
@Component
public class CGEnterActivity extends CGMessage{
	
	/** 活动Id */
	private int activityId;
	
	public CGEnterActivity (){
	}
	
	public CGEnterActivity (
			int activityId ){
			this.activityId = activityId;
	}
	
	@Override
	protected boolean readImpl() {
		activityId = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(activityId);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_ENTER_ACTIVITY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_ENTER_ACTIVITY";
	}

	public int getActivityId(){
		return activityId;
	}
		
	public void setActivityId(int activityId){
		this.activityId = activityId;
	}

	@Override
	public void execute() {
	}
}