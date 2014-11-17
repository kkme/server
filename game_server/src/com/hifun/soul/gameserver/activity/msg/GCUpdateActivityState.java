package com.hifun.soul.gameserver.activity.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新活动状态
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateActivityState extends GCMessage{
	
	/** 活动Id */
	private int id;
	/** 活动类型 */
	private int activityType;
	/** 活动状态：参见ActivityState枚举 */
	private int state;

	public GCUpdateActivityState (){
	}
	
	public GCUpdateActivityState (
			int id,
			int activityType,
			int state ){
			this.id = id;
			this.activityType = activityType;
			this.state = state;
	}

	@Override
	protected boolean readImpl() {
		id = readInteger();
		activityType = readInteger();
		state = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(id);
		writeInteger(activityType);
		writeInteger(state);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_ACTIVITY_STATE;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_ACTIVITY_STATE";
	}

	public int getId(){
		return id;
	}
		
	public void setId(int id){
		this.id = id;
	}

	public int getActivityType(){
		return activityType;
	}
		
	public void setActivityType(int activityType){
		this.activityType = activityType;
	}

	public int getState(){
		return state;
	}
		
	public void setState(int state){
		this.state = state;
	}
}