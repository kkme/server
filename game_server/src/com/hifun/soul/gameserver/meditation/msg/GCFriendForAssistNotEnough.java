package com.hifun.soul.gameserver.meditation.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 在线好友数太少
 *
 * @author SevenSoul
 */
@Component
public class GCFriendForAssistNotEnough extends GCMessage{
	
	/** 发出的申请数 */
	private int applySendNum;

	public GCFriendForAssistNotEnough (){
	}
	
	public GCFriendForAssistNotEnough (
			int applySendNum ){
			this.applySendNum = applySendNum;
	}

	@Override
	protected boolean readImpl() {
		applySendNum = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(applySendNum);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FRIEND_FOR_ASSIST_NOT_ENOUGH;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FRIEND_FOR_ASSIST_NOT_ENOUGH";
	}

	public int getApplySendNum(){
		return applySendNum;
	}
		
	public void setApplySendNum(int applySendNum){
		this.applySendNum = applySendNum;
	}
}