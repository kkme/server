package com.hifun.soul.gameserver.activity.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新独立活动列表
 *
 * @author SevenSoul
 */
@Component
public class GCAloneActivityList extends GCMessage{
	
	/** 活动列表 */
	private com.hifun.soul.gameserver.activity.model.ActivityBaseInfo[] activityBaseInfoList;

	public GCAloneActivityList (){
	}
	
	public GCAloneActivityList (
			com.hifun.soul.gameserver.activity.model.ActivityBaseInfo[] activityBaseInfoList ){
			this.activityBaseInfoList = activityBaseInfoList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		activityBaseInfoList = new com.hifun.soul.gameserver.activity.model.ActivityBaseInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.activity.model.ActivityBaseInfo objactivityBaseInfoList = new com.hifun.soul.gameserver.activity.model.ActivityBaseInfo();
			activityBaseInfoList[i] = objactivityBaseInfoList;
					objactivityBaseInfoList.setId(readInteger());
							objactivityBaseInfoList.setActivityType(readInteger());
							objactivityBaseInfoList.setIconId(readInteger());
							objactivityBaseInfoList.setName(readString());
							objactivityBaseInfoList.setState(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(activityBaseInfoList.length);
	for(int i=0; i<activityBaseInfoList.length; i++){
	com.hifun.soul.gameserver.activity.model.ActivityBaseInfo objactivityBaseInfoList = activityBaseInfoList[i];
				writeInteger(objactivityBaseInfoList.getId());
				writeInteger(objactivityBaseInfoList.getActivityType());
				writeInteger(objactivityBaseInfoList.getIconId());
				writeString(objactivityBaseInfoList.getName());
				writeInteger(objactivityBaseInfoList.getState());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ALONE_ACTIVITY_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ALONE_ACTIVITY_LIST";
	}

	public com.hifun.soul.gameserver.activity.model.ActivityBaseInfo[] getActivityBaseInfoList(){
		return activityBaseInfoList;
	}

	public void setActivityBaseInfoList(com.hifun.soul.gameserver.activity.model.ActivityBaseInfo[] activityBaseInfoList){
		this.activityBaseInfoList = activityBaseInfoList;
	}	
}