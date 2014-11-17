package com.hifun.soul.gameserver.vip.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 显示充值活动信息
 *
 * @author SevenSoul
 */
@Component
public class GCShowChargeActivity extends GCMessage{
	
	/** 充值活动信息 */
	private String[] activitys;

	public GCShowChargeActivity (){
	}
	
	public GCShowChargeActivity (
			String[] activitys ){
			this.activitys = activitys;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		activitys = new String[count];
		for(int i=0; i<count; i++){
			activitys[i] = readString();
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(activitys.length);
	for(int i=0; i<activitys.length; i++){
	String objactivitys = activitys[i];
			writeString(objactivitys);
}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_CHARGE_ACTIVITY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_CHARGE_ACTIVITY";
	}

	public String[] getActivitys(){
		return activitys;
	}

	public void setActivitys(String[] activitys){
		this.activitys = activitys;
	}	
}