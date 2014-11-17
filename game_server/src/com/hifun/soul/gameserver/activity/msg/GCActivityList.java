package com.hifun.soul.gameserver.activity.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新活动列表
 *
 * @author SevenSoul
 */
@Component
public class GCActivityList extends GCMessage{
	
	/** 活动列表 */
	private com.hifun.soul.gameserver.activity.model.ActivityData[] Activities;

	public GCActivityList (){
	}
	
	public GCActivityList (
			com.hifun.soul.gameserver.activity.model.ActivityData[] Activities ){
			this.Activities = Activities;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		Activities = new com.hifun.soul.gameserver.activity.model.ActivityData[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.activity.model.ActivityData objActivities = new com.hifun.soul.gameserver.activity.model.ActivityData();
			Activities[i] = objActivities;
					objActivities.setId(readInteger());
							objActivities.setName(readString());
							objActivities.setSimpleDesc(readString());
							objActivities.setActivityShowType(readInteger());
							objActivities.setOpenLevel(readInteger());
							objActivities.setActiveTimeInfo(readString());
							objActivities.setState(readInteger());
							objActivities.setIconId(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(Activities.length);
	for(int i=0; i<Activities.length; i++){
	com.hifun.soul.gameserver.activity.model.ActivityData objActivities = Activities[i];
				writeInteger(objActivities.getId());
				writeString(objActivities.getName());
				writeString(objActivities.getSimpleDesc());
				writeInteger(objActivities.getActivityShowType());
				writeInteger(objActivities.getOpenLevel());
				writeString(objActivities.getActiveTimeInfo());
				writeInteger(objActivities.getState());
				writeInteger(objActivities.getIconId());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_ACTIVITY_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_ACTIVITY_LIST";
	}

	public com.hifun.soul.gameserver.activity.model.ActivityData[] getActivities(){
		return Activities;
	}

	public void setActivities(com.hifun.soul.gameserver.activity.model.ActivityData[] Activities){
		this.Activities = Activities;
	}	
}