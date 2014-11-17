package com.hifun.soul.gameserver.activity.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求获取活动列表
 * 
 * @author SevenSoul
 */
@Component
public class CGQueryActivityList extends CGMessage{
	
	/** 活动显示类型 */
	private int activityShowType;
	
	public CGQueryActivityList (){
	}
	
	public CGQueryActivityList (
			int activityShowType ){
			this.activityShowType = activityShowType;
	}
	
	@Override
	protected boolean readImpl() {
		activityShowType = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(activityShowType);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_QUERY_ACTIVITY_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_QUERY_ACTIVITY_LIST";
	}

	public int getActivityShowType(){
		return activityShowType;
	}
		
	public void setActivityShowType(int activityShowType){
		this.activityShowType = activityShowType;
	}

	@Override
	public void execute() {
	}
}